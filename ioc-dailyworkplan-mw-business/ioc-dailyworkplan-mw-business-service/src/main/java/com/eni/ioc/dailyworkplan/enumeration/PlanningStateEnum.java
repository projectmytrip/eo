package com.eni.ioc.dailyworkplan.enumeration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public enum PlanningStateEnum {
	STATE_01("STATE_01"),
	STATE_02("STATE_02"),
	STATE_03("STATE_03"),
	STATE_04("STATE_04");

	private String state;

	private static Map<String, PlanningStateEnum> map = new LinkedHashMap<String, PlanningStateEnum>();
	static {
		for (PlanningStateEnum state : PlanningStateEnum.values()) {
			map.put(state.getState(), state);
		}
		map = Collections.unmodifiableMap(map);
	}

	private PlanningStateEnum(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static PlanningStateEnum getEnum(String state) {
		PlanningStateEnum toReturn = null;
		if (map.containsKey(state)) {
			toReturn = map.get(state);
		}
		return toReturn;
	}

}
