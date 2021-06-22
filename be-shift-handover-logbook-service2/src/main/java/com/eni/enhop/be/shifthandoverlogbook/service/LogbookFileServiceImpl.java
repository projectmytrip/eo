package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eni.enhop.be.common.controller.ServiceException;
import com.eni.enhop.be.common.core.bean.FileDownloadBean;
import com.eni.enhop.be.common.core.proxy.ProxyException;
import com.eni.enhop.be.common.core.proxy.ProxyRequest;
import com.eni.enhop.be.common.restclient.RCHeaders;
import com.eni.enhop.be.common.restclient.RestClientUtils;
import com.eni.enhop.be.files.dto.FileMetaDTO;
import com.eni.enhop.be.files.proxy.FilesProxy;
import com.eni.enhop.be.shifthandoverlogbook.exception.GenericException;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookTranslation;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookWhitelist;
import com.eni.enhop.be.shifthandoverlogbook.repository.LogbookTranslationRepository;
import com.eni.enhop.be.shifthandoverlogbook.security.EniPrincipal;
import com.eni.enhop.be.shifthandoverlogbook.service.dto.LogbookPdfRequestDTO;
import com.eni.enhop.be.users.dto.EniUser;
import com.eni.enhop.be.users.proxy.UserServiceProxy;

@Service
public class LogbookFileServiceImpl implements LogbookFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogbookFileService.class);

	@Autowired
	FilesProxy filesProxy;

	@Autowired
	EmailService emailService;

	@Autowired
	UserServiceProxy userProxy;

	@Autowired
	LogbookWhitelistService whitelistService;

	@Autowired
	LogbookTranslationRepository logbookTranslationRepository;

	@Value("${microservice.files.generatepdf.templateid.en}")
	private String templateEn;

	@Value("${microservice.files.generatepdf.templateid.it}")
	private String templateIt;

	@Value("${default.whitelist}")
	private String defWhiteList;

	@Value("${default.emailTemplate}")
	private String emailTemplate;

	@Override
	public FileMetaDTO generatePdf(LogbookPdfRequestDTO dto, String userLang, String type) {

		ProxyRequest proxyRequest = new ProxyRequest();
		if (dto == null) {
			dto = new LogbookPdfRequestDTO();
		}

		String template = getTemplateIdByLangAndType(userLang, type);
		dto.setTemplateId(template);
		proxyRequest.setEntity(dto);
		LOGGER.debug("sending {}", dto);

		try {
			FileMetaDTO fileMetaDTO = filesProxy.registrePdf(proxyRequest);
			LOGGER.debug("filemetaDTO {}", fileMetaDTO);
			return fileMetaDTO;
		} catch (ProxyException e) {
			LOGGER.error("error during registrePdf", e);
		}

		return null;
	}

	private String getTemplateIdByLangAndType(String userLang, String type) {
		return (userLang != null && "en".equalsIgnoreCase(userLang)) ? templateEn : templateIt;
	}

	@Override
	public FileDownloadBean download(String metaId) throws GenericException {
		try {
			FileDownloadBean res = filesProxy.download(new ProxyRequest(), metaId);

			if (res == null || res.getFile() == null)
				throw new GenericException("Error during download the pdf, the file returned is null");

			LOGGER.info("space {}", res.getFile().getTotalSpace());
			return res;
		} catch (ProxyException e) {
			throw new GenericException(e);
		}
	}

	@Override
	public FileMetaDTO prepareFile(LogbookPage logbookPage, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userLang = "";
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof EniPrincipal) {
				EniPrincipal eniPrincipal = (EniPrincipal) authentication.getPrincipal();
				Locale locale = eniPrincipal.getLocale();
				userLang = locale.getLanguage();
			} else {
				userLang = "it";
			}
		}

		List<LogbookTranslation> translations = logbookTranslationRepository.findByLanguage(userLang);

		String type = translations.stream().filter(t -> logbookPage.getLogbookType().equalsIgnoreCase(t.getCode()))
				.map(t -> t.getTranslation()).findAny().orElse(logbookPage.getLogbookType());

		return generatePdf(generateFileDto(logbookPage, translations), userLang, type);
	}

	private LogbookPdfRequestDTO generateFileDto(LogbookPage logbookPage, List<LogbookTranslation> translations) {
		return new LogbookPdfRequestDTO(logbookPage, translations);
	}

	@Override
	public void sendEmailToWhitelist(LogbookPage p, String whitelist, HttpServletRequest request) {
		try {
			String wlName = p.getLogbookType() != null ? p.getLogbookType() : defWhiteList;
			List<LogbookWhitelist> wl = whitelistService.getWhitelistByName(wlName);

			List<EniUser> usersInvolved = getUsers(wl, request);
			List<String> emailRecipients = formatRecipients(wl, usersInvolved, true);
			List<String> carbonCopies = formatRecipients(wl, usersInvolved, false);

			String template = getEmailTemplate(p);

			emailService.sendEmail(download(p.getFileId()), template, emailRecipients, carbonCopies, Locale.ITALY, p);
		} catch (GenericException e) {
			LOGGER.error("could not send email", e);
		}
	}

	private String getEmailTemplate(LogbookPage p) {
		return emailTemplate;
	}

	private List<EniUser> getUsers(List<LogbookWhitelist> whitelist, HttpServletRequest request) {
		List<EniUser> users = new ArrayList<>();
		try {
			if (whitelist != null && !whitelist.isEmpty()) {
				List<Long> usersIds = new ArrayList<>();
				for (LogbookWhitelist w : whitelist) {
					usersIds.add(w.getIdUser());
				}
				RCHeaders headers = RestClientUtils.fromRequest(request);
				users = userProxy.getUsers(usersIds, headers);
				return users;
			} else {
				LOGGER.warn("Empty whitelist!");
			}
		} catch (ServiceException e) {
			LOGGER.error("", e);
		}
		return users;
	}

	private List<String> formatRecipients(List<LogbookWhitelist> whitelist, List<EniUser> usersInvolved,
			boolean isRecipient) {
		List<String> recipients = new ArrayList<>();

		if (usersInvolved == null || whitelist == null || usersInvolved.isEmpty() || whitelist.isEmpty()) {
			LOGGER.warn("Empty whitelist!");
			return recipients;
		}

		LOGGER.info("Format recipients {}", usersInvolved);

		List<Long> ids = whitelist.stream().filter(wl -> Boolean.compare(isRecipient, wl.isPrimaryRecipient()) == 0)
				.map(LogbookWhitelist::getIdUser).collect(Collectors.toList());

		recipients = usersInvolved.stream().filter(u -> ids.contains(u.getId())).map(EniUser::getEmail)
				.collect(Collectors.toList());

		LOGGER.debug("found {}", recipients);

		return recipients;
	}

}
