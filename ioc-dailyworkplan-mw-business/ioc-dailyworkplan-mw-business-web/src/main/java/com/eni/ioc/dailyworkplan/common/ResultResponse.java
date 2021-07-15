package com.eni.ioc.dailyworkplan.common;

public class ResultResponse {
	private long code;
	private String message;
	private String description;

	public ResultResponse() {
	}

	public ResultResponse(long code, String message) {
		this.setCode(code);
		this.setMessage(message);
		this.setDescription("");
	}

	public ResultResponse(long code, String message, String description) {
		this.setCode(code);
		this.setMessage(message);
		this.setDescription(description);
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
