package com.eni.enhop.be.shifthandoverlogbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class LogbookPageException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private LogbookPageErrorCode code;

	public LogbookPageException(LogbookPageErrorCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public LogbookPageException(LogbookPageErrorCode code, String message) {
		this(code, message, null);
	}

	public LogbookPageException(LogbookPageErrorCode code, Throwable cause) {
		this(code, null, cause);
	}

	public LogbookPageErrorCode getCode() {
		return code;
	}

	public void setCode(LogbookPageErrorCode code) {
		this.code = code;
	}

}
