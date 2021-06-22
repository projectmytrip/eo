package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.eni.enhop.be.files.dto.FileMetaDTO;
import com.eni.enhop.be.shifthandoverlogbook.exception.LogbookPageAlreadyExistsException;
import com.eni.enhop.be.shifthandoverlogbook.exception.LogbookPageNotFoundException;
import com.eni.enhop.be.shifthandoverlogbook.exception.LogbookPageReadOnlyException;
import com.eni.enhop.be.shifthandoverlogbook.exception.LogbookPageUnauthorizedException;
import com.eni.enhop.be.shifthandoverlogbook.mapper.LogbookPageSummaryMapper;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPageState;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPageSummary;
import com.eni.enhop.be.shifthandoverlogbook.model.QLogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.repository.LogbookPageRepository;
import com.eni.enhop.be.shifthandoverlogbook.security.EniPrincipal;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class LogbookPageServiceImpl implements LogbookPageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogbookPageService.class);

	@Autowired
	private LogbookFileService logbookFileService;

	@Autowired
	private LogbookPageRepository logbookPageRepository;

	@Autowired
	private LogbookPageSummaryMapper logbookPageSummaryMapper;

	@Override
	public Page<LogbookPageSummary> searchPages(Predicate predicate, Pageable pageable) {
		LOGGER.info("Search pages by predicate: {}, pageable: {}", predicate, pageable);
		BooleanExpression isActiveExpression = QLogbookPage.logbookPage.isActive.eq(true);
		BooleanBuilder bb = new BooleanBuilder();
		bb.and(predicate);
		bb.and(isActiveExpression);
		Page<LogbookPageSummary> logbookPages = logbookPageRepository.findAll(bb, pageable)
				.map(logbookPageSummaryMapper::toLogbookSummary);
		LOGGER.debug("Pages: {}", logbookPages);
		return logbookPages;
	}

	@Override
	public Page<LogbookPageSummary> searchLocationPages(long locationId, String logbookType, Predicate predicate,
			Pageable pageable) {
		LOGGER.info("Search location pages by locationId: {}, predicate: {} and peageable: {}", locationId, predicate,
				pageable);

		BooleanExpression expLogbookLocationId = QLogbookPage.logbookPage.locationId.eq(locationId);
		BooleanExpression expLogbookLogbookType = QLogbookPage.logbookPage.logbookType.eq(logbookType);
		BooleanBuilder bb = new BooleanBuilder();
		bb.and(predicate);
		bb.and(expLogbookLocationId);
		bb.and(expLogbookLogbookType);

		return searchPages(bb, pageable);
	}

	@Override
	public Optional<LogbookPage> get(String logbookType, long logbookPageId) {
		LOGGER.info("Get the logbook: {}", logbookPageId);

		// we have to check if the logbook requested has the right logbook type
		Optional<LogbookPage> page = logbookPageRepository.findByLogbookTypeAndIdAndIsActiveTrue(logbookType,
				logbookPageId);

		LOGGER.debug("The logkbook page {} was found", page);
		return page;
	}

	@Override
	public Optional<LogbookPage> delete(String logbookType, long logbookPageId) {
		LOGGER.info("Delete the logbook: {}", logbookPageId);

		EniPrincipal eniPrincipal = getCurrentUser();
		Optional<LogbookPage> page = logbookPageRepository.findByLogbookTypeAndIdAndIsActiveTrue(logbookType,
				logbookPageId);

		page.ifPresent(p -> {
			if (p.getState() == LogbookPageState.OPENED
					|| StringUtils.equalsIgnoreCase(p.getCreatedBy(), eniPrincipal.getUsername())) {
				p.setActive(false);
				logbookPageRepository.save(p);
			} else {
				throw new LogbookPageReadOnlyException("The user is not the owner of the logbook");
			}
		});

		return page;
	}

	@Override
	public LogbookPage update(long locationId, String logbookType, LogbookPage logbookPage) {
		LOGGER.info("Update the logbook: {}", logbookPage.toString());
		Assert.notNull(logbookPage, "The logbook page must not be null");

		LOGGER.info(" logbook: has id " + logbookPage.getId());
		logbookPageRepository.findByLogbookTypeAndIdAndIsActiveTrue(logbookType, logbookPage.getId()).orElseThrow(
				() -> new LogbookPageNotFoundException("The logbook page " + logbookPage.getId() + "was not found"));

		LogbookPage savedPage = logbookPageRepository.save(logbookPage);

		LOGGER.debug("update logbook page: {}", savedPage);
		return savedPage;
	}

	@Override
	public LogbookPage save(long locationId, String logbookType, LogbookPage logbookPage, HttpServletRequest request) {
		LOGGER.info("Save the logbook: {}", logbookPage.toString());
		Assert.notNull(logbookPage, "The logbook page must not be null");

		EniPrincipal eniPrincipal = getCurrentUser();

		if (logbookPage.getId() != null) {
			LogbookPage toUpdate = logbookPageRepository
					.findByLogbookTypeAndIdAndIsActiveTrue(logbookType, logbookPage.getId())
					.orElseThrow(() -> new LogbookPageNotFoundException(
							"The logbook page " + logbookPage.getId() + " was not found"));
			if (LogbookPageState.CLOSED.equals(toUpdate.getState())
					&& !StringUtils.equalsIgnoreCase(eniPrincipal.getUsername(), logbookPage.getCreatedBy())) {
				throw new LogbookPageReadOnlyException("The logbook page can not be modified because it is closed");
			}
		} else {
			// check if a logbook page already exists
			Long plantSectionId = logbookPage.getPlantSection() != null ? logbookPage.getPlantSection().getId() : null;
			logbookPageRepository.findByExistingLogbookPage(locationId, logbookPage.getLogbookType(),
					logbookPage.getLogbookDate(), logbookPage.getWorkShift().getId(), plantSectionId).ifPresent(l -> {
						throw new LogbookPageAlreadyExistsException("The logbook page already exists");
					});

			// create a new logbook page
			if (logbookPage.getState() == null) {
				logbookPage.setState(LogbookPageState.OPENED);
			}
			String creatorDisplayName = Stream.of(eniPrincipal.getName(), eniPrincipal.getSurname())
					.filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(" "));
			logbookPage.setCreatorDisplayName(creatorDisplayName);
			logbookPage.setLocationId(locationId);
		}

		FileMetaDTO d = logbookFileService.prepareFile(logbookPage, request);

		if (d == null || d.getMetaId() == null) {
			LOGGER.error("EMPTY ID, NO FILE CREATED!!!!");
		} else {
			logbookPage.setFileId(d.getMetaId());
		}

		LogbookPage savedPage = logbookPageRepository.save(logbookPage);

		LOGGER.debug("Saved logbook page: {}", savedPage);
		return savedPage;
	}

	@Override
	public LogbookPage confirm(long locationId, String logbookType, LogbookPage logbookPage,
			HttpServletRequest request) {
		logbookPage.setState(LogbookPageState.CLOSED);
		LogbookPage savedPage = save(locationId, logbookType, logbookPage, request);

		asyncSendToWhitelist(savedPage, savedPage.getLogbookType(), request);

		return savedPage;
	}

	private EniPrincipal getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !EniPrincipal.class.isInstance(authentication.getPrincipal())) {
			throw new LogbookPageUnauthorizedException("The user is not authenticated");
		}

		return (EniPrincipal) authentication.getPrincipal();
	}

	@Override
	@Async
	public void asyncSendToWhitelist(LogbookPage p, String defaultWhitelist, HttpServletRequest request) {
		LOGGER.info("asyncSendToWhitelist: {}, {}", p, defaultWhitelist);
		// create pdf
//		FileMetaDTO d = logbookFileService.prepareFile(p);
//		LOGGER.info("file created: {}", d);
		// send mails with attachment
		logbookFileService.sendEmailToWhitelist(p, defaultWhitelist, request);
		LOGGER.info("email sent");
	}

}
