package com.eni.ioc.be.email.common;

public enum ResultCode {
	BAD_REQUEST("400");

	private String code;

	ResultCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
