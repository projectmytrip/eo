package com.eni.enhop.be.shifthandoverlogbook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPageSummary;
import com.eni.enhop.be.shifthandoverlogbook.service.LogbookPageService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("locations/{locationId}/logbook/{logbookType}/pages")
public class LogbookPagesController {

	@Autowired
	private LogbookPageService logbookPageService;

	@Value("default.whitelist")
	private String defaultWhitelist;

	private static final Logger LOGGER = LoggerFactory.getLogger(LogbookPagesController.class);

	@GetMapping
	@PreAuthorize("isMember(#locationId) and canView(#logbookType)")
	public Page<LogbookPageSummary> searchPages(@PathVariable long locationId, @PathVariable String logbookType,
			@QuerydslPredicate(root = LogbookPage.class) Predicate predicate, @PageableDefault Pageable pageable) {
		Page<LogbookPageSummary> pages = logbookPageService.searchLocationPages(locationId, logbookType, predicate,
				pageable);
		return pages;
	}

	@GetMapping("{logbookPageId}")
	@PreAuthorize("isMember(#locationId) and canView(#logbookType)")
	public ResponseEntity<LogbookPage> get(@PathVariable long locationId, @PathVariable String logbookType,
			@PathVariable long logbookPageId) {
		return ResponseEntity.of(logbookPageService.get(logbookType, logbookPageId));
	}

	@DeleteMapping("{logbookPageId}")
	@PreAuthorize("isMember(#locationId) and canView(#logbookType)")
	public ResponseEntity<LogbookPage> delete(@PathVariable long locationId, @PathVariable String logbookType,
			@PathVariable long logbookPageId) {
		return ResponseEntity.of(logbookPageService.delete(logbookType, logbookPageId));
	}

	@PostMapping("/save")
	@PreAuthorize("isMember(#locationId) and canWrite(#logbookType)")
	public ResponseEntity<LogbookPage> save(@PathVariable long locationId, @PathVariable String logbookType,
			@RequestBody @Valid LogbookPage logbookPage, HttpServletRequest request) {
		
		LogbookPage p = logbookPageService.save(locationId, logbookType, logbookPage, request);

		LOGGER.debug("returning {}", p);

		return ResponseEntity.ok(p);
	}

	@PostMapping("/confirm")
	@PreAuthorize("isMember(#locationId) and canWrite(#logbookType)")
	public ResponseEntity<LogbookPage> confirm(@PathVariable long locationId, @PathVariable String logbookType,
			@RequestBody @Valid LogbookPage logbookPage, HttpServletRequest request) {
		
		LogbookPage p = logbookPageService.confirm(locationId, logbookType, logbookPage, request);

		LOGGER.debug("returning {}", p);

		return ResponseEntity.ok(p);
	}

	/*
	 * @GetMapping("/test") public String test(HttpServletRequest request) {
	 * LOGGER.info("fake save"); LogbookPage p = new ShiftLeaderLogbookPage();
	 * logbookPageService.asyncSendToWhitelist(p, defaultWhitelist, request);
	 * LOGGER.info("returning {}", p);
	 * 
	 * return "Ok"; }
	 */
}
