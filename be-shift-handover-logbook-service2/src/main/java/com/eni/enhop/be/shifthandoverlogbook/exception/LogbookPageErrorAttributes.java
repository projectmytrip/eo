package com.eni.enhop.be.shifthandoverlogbook.exception;

import java.util.Map;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class LogbookPageErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
		Throwable throwable = getError(webRequest);
		if (throwable instanceof LogbookPageException) {
			LogbookPageException logbookPageException = (LogbookPageException) throwable;
			if (logbookPageException.getCode() != null) {
				errorAttributes.put("code", logbookPageException.getCode());
			}
		}

		return errorAttributes;
	}
}