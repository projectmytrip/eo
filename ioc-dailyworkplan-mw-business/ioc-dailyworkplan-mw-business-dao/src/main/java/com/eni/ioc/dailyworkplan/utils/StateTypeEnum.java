package com.eni.ioc.dailyworkplan.utils;

public enum StateTypeEnum {
	TODO("TODO", 0),
	SAVED("SAVED", 1),
	PENDING("PENDING", 2),
	APPROVED("APPROVED", 3),
	REJECTED("REJECTED", 4),
	SHERPA_SAVED("SHERPA_SAVED", 5);

	private String value;
	private Integer id;

	StateTypeEnum(String value, Integer id) {
		this.value = value;
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public Integer getId() {
		return id;
	}
}
