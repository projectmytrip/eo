package com.eni.enhop.be.shifthandoverlogbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LogbookPageNotFoundException extends LogbookPageException {

	private static final long serialVersionUID = 5093057619360761799L;

	public LogbookPageNotFoundException(String message, Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_NOT_FOUND, message, cause);
	}

	public LogbookPageNotFoundException(String message) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_NOT_FOUND, message);
	}

	public LogbookPageNotFoundException(Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_NOT_FOUND, cause);
	}
}