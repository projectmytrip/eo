package com.eni.enhop.be.shifthandoverlogbook.service;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eni.enhop.be.common.core.bean.FileDownloadBean;
import com.eni.enhop.be.common.core.proxy.ProxyException;
import com.eni.enhop.be.common.core.proxy.ProxyRequest;
import com.eni.enhop.be.common.core.proxy.ProxySerializer;
import com.eni.enhop.be.email.dto.input.Notification;
import com.eni.enhop.be.email.proxy.EmailProxy;
import com.eni.enhop.be.shifthandoverlogbook.exception.GenericException;
import com.eni.enhop.be.shifthandoverlogbook.model.EmailTemplate;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookTranslation;
import com.eni.enhop.be.shifthandoverlogbook.repository.EmailTemplateRepository;
import com.eni.enhop.be.shifthandoverlogbook.repository.LogbookTranslationRepository;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	EmailProxy mailProxy;

	@Autowired
	EmailTemplateRepository emailTemplateRepo;

	@Autowired
	LogbookTranslationRepository logbookTranslationRepository;

	@Value("${mail.attachment.filename}")
	private String mailFileName;

	@Value("${microservice.email.send.filekeyname}")
	private String fileKeyParam;

	@Value("${microservice.email.send.datakeyname}")
	private String dataKeyParam;

	private static final String DEFAULT_STRING_FORMAT = "%s %s";

	// wrapper
	@Override
	public void sendEmail(FileDownloadBean file, String emailTemplate, List<String> emailRecipients,
			List<String> carbonCopies, Locale locale, LogbookPage p) throws GenericException {

		if (file == null || file.getFile() == null)
			throw new GenericException("Error during download the pdf, the file returned is null");

		Notification notification = getNotificationBody(emailTemplate, emailRecipients, carbonCopies, locale, p);

		String dateString = DateTimeFormatter.ofPattern("ddMMuuuu").format(p.getLogbookDate());
		String workShift = p.getWorkShift().getFileName();
		String fileName = String.format(mailFileName, dateString, workShift);
		File newFile = new File(fileName);
		if (newFile.exists()) {
			newFile.delete();
			file.getFile().renameTo(newFile);
		}

		// send request
		sendEMailWhitAttachment(notification, file, fileName);
	}

	private Notification getNotificationBody(String emailTemplate, List<String> emailRecipients,
			List<String> carbonCopies, Locale locale, LogbookPage p) throws GenericException {

		EmailTemplate emailTemp = findEmailTemplateByNameAndLocale(emailTemplate, locale);

		String placeholderDate = DateTimeFormatter.ofPattern("dd/MM/uuuu").format(p.getLogbookDate()) + " - "
				+ p.getWorkShift().getName() + " - " + p.getCreatorDisplayName();

		String translation = getTranslation(p.getLogbookType(), locale.getLanguage());

		String placeholder = String.format(DEFAULT_STRING_FORMAT, translation, placeholderDate);
		String subject = String.format(emailTemp.getSubject(), placeholder);

		Notification notification = new Notification();
		notification.setSubject(subject);
		notification.setRecipients(emailRecipients);
		notification.setCarbonCopy(carbonCopies);
		notification.setMessageHtml(String.format(emailTemp.getBody(), translation, placeholderDate));

		return notification;
	}

	private String getTranslation(String word, String language) {
		List<LogbookTranslation> typeTranslations = logbookTranslationRepository.findByCodeAndLanguage(word, language);
		if (typeTranslations != null && !typeTranslations.isEmpty()) {
			return typeTranslations.get(0).getTranslation();
		} else {
			return "";
		}
	}

	private EmailTemplate findEmailTemplateByNameAndLocale(String emailTemplate, Locale locale)
			throws GenericException {

		if (locale == null) {
			LOGGER.warn("Use default language {}", Locale.ITALIAN);
			locale = Locale.ITALIAN;
		}
		EmailTemplate res = emailTemplateRepo.findByNameAndLocale(emailTemplate, locale);
		LOGGER.debug("The following template was found {}", res);
		if (res == null) {
			LOGGER.warn("Template email {} for locale {}. Trying with the default template...", emailTemplate, locale);
			res = emailTemplateRepo.findByNameAndIsDefault(emailTemplate, true);
			if (res == null) {
				throw new GenericException("Template email not found!");
			}
		}
		return res;

	}

	// sends an email with attachment
	@Override
	public void sendEMailWhitAttachment(Notification notification, FileDownloadBean attachment, String attachemntName) {
		LOGGER.info("Send email by notification: {}, attachment: {}, attachementName: {}", notification, attachment,
				attachemntName);
		// preapre request
		ProxyRequest proxyRequest = new ProxyRequest();
		Map<String, String> queryString = new HashMap<>();

		queryString.put(dataKeyParam, StringEscapeUtils.escapeJson(ProxySerializer.serializerToJson(notification)));
		proxyRequest.setQueryString(queryString);

		// send request
		LOGGER.info(
				"Call send mail using the mailProxy by proxyRequest: {}, fileKeyParam: {}, attachement: {}, attachmentName: {}",
				proxyRequest, fileKeyParam, attachment, attachemntName);
		try {
			mailProxy.send(proxyRequest, fileKeyParam, attachment, attachemntName);
		} catch (ProxyException e) {
			LOGGER.error("could not send mail with attachment", e);
		}
	}

}
