package com.eni.ioc.dailyworkplan.utils;

public enum TemplateMailEnum {
	INSERTION("INSERTION"),
	APPROVED("APPROVED"),
	SHERPA("SHERPA");

	private String value;

	private TemplateMailEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
