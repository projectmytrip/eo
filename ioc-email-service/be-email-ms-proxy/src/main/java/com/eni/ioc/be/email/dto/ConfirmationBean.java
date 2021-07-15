package com.eni.ioc.be.email.dto;

import com.eni.ioc.be.email.dto.NotificationServiceConstants.CODES;

public class ConfirmationBean {
	private static final long serialVersionUID = 2237716044373838822L;
	private CODES code;
	private String reason;

	public CODES getCode() {
		return code;
	}

	public void setCode(CODES code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
