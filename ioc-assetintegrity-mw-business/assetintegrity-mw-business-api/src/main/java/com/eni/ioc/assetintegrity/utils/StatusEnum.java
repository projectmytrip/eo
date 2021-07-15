package com.eni.ioc.assetintegrity.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public enum StatusEnum {

	NORMAL(1, "Normal"),
	WARNING(2, "Warning"),
	ALERT(3, "Alert");

	private Integer value;
	private String label;

	private static Map<Integer, StatusEnum> map = new LinkedHashMap<Integer, StatusEnum>();

	static {
		for (StatusEnum s : StatusEnum.values()) {
			map.put(s.getValue(), s);
		}
	}

	private StatusEnum(Integer value, String label) {
		this.value = value;
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static StatusEnum getEnum(Integer value) {
		StatusEnum toReturn = null;
		if(map.containsKey(value)) {
			toReturn = map.get(value);
		}
		return toReturn;
	}

}