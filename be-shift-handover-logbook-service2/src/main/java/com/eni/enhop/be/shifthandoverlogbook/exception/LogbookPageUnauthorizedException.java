package com.eni.enhop.be.shifthandoverlogbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LogbookPageUnauthorizedException extends LogbookPageException {

	private static final long serialVersionUID = 8733888177038966397L;

	public LogbookPageUnauthorizedException(String message, Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_ALREADY_EXISTS, message, cause);
	}

	public LogbookPageUnauthorizedException(String message) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_ALREADY_EXISTS, message);
	}

	public LogbookPageUnauthorizedException(Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_ALREADY_EXISTS, cause);
	}
}
