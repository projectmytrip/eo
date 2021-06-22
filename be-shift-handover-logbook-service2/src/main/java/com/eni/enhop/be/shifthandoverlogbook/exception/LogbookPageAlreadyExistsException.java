package com.eni.enhop.be.shifthandoverlogbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LogbookPageAlreadyExistsException extends LogbookPageException {

	private static final long serialVersionUID = 5093057619360761799L;

	public LogbookPageAlreadyExistsException(String message, Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_ALREADY_EXISTS, message, cause);
	}

	public LogbookPageAlreadyExistsException(String message) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_ALREADY_EXISTS, message);
	}

	public LogbookPageAlreadyExistsException(Throwable cause) {
		super(LogbookPageErrorCode.LOGBOOK_PAGE_ALREADY_EXISTS, cause);
	}
}