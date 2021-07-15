package com.eni.ioc.dailyworkplan.utils;

public class DailyWorkPlanException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CodeError code;
	String description;

	public enum CodeError {
		conversionError, nullPointerError, wrongParameter, dbError, formatError
	}

	public DailyWorkPlanException(CodeError code, String description) {
		super(description);
		this.code = code;
		this.description = description;
	}

	public CodeError getCode() {
		return code;
	}

	public void setCode(CodeError code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
