package com.eni.enhop.be.shifthandoverlogbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class LogbookPageReadOnlyException extends LogbookPageException {

	private static final long serialVersionUID = 5093057619360761799L;

	public LogbookPageReadOnlyException(String message, Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_READ_ONLY, message, cause);
	}

	public LogbookPageReadOnlyException(String message) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_READ_ONLY, message);
	}

	public LogbookPageReadOnlyException(Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_READ_ONLY, cause);
	}
}