package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPageSummary;
import com.querydsl.core.types.Predicate;

public interface LogbookPageService {

	Page<LogbookPageSummary> searchPages(Predicate predicate, Pageable pageable);

	Page<LogbookPageSummary> searchLocationPages(long locationId, String logbookType, Predicate predicate,
			Pageable pageable);

	Optional<LogbookPage> get(String logbookType, long logbookPageId);

	Optional<LogbookPage> delete(String logbookType, long logbookPageId);

	LogbookPage save(long locationId, String logbookType, LogbookPage logbookPage, HttpServletRequest request);

	LogbookPage confirm(long locationId, String logbookType, LogbookPage logbookPage, HttpServletRequest request);

	LogbookPage update(long locationId, String logbookType, LogbookPage logbookPage);

	void asyncSendToWhitelist(LogbookPage p, String defaultWhitelist, HttpServletRequest request);

}
