package com.eni.ioc.assetintegrity.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum BacklogColoreEnum {
	GREEN("green", "verde", "rgba(0, 111, 75, 0.6)", 2),
	YELLOW("yellow", "giallo", "rgba(251, 176, 51, 0.6)", 1),
	RED("red", "rosso", "rgba(152, 0, 46, 0.6)", 0),
	GRAY("gray", "grigio", "rgba(195,195,193,0.6)", 3);

	private String color; // colore in inglese
	private String italianColor; // colore in italiano
	private String codeColor; // codice rgba del colore 
	private Integer priority; // priorità del colore

	private static Map<String, BacklogColoreEnum> map = new LinkedHashMap<String, BacklogColoreEnum>();
	static {
		for (BacklogColoreEnum color : BacklogColoreEnum.values()) {
			map.put(color.getColor(), color);
		}
		map = Collections.unmodifiableMap(map);
	}

	private static Map<Integer, BacklogColoreEnum> priorityMap = new HashMap<>();
	static {
		for (BacklogColoreEnum color : BacklogColoreEnum.values()) {
			priorityMap.put(color.getPriority(), color);
		}
	}

	private BacklogColoreEnum(String color, String italianColor, String codeColor, Integer priority) {
		this.color = color;
		this.italianColor = italianColor;
		this.codeColor = codeColor;
		this.priority = priority;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getItalianColor() {
		return italianColor;
	}

	public void setItalianColor(String italianColor) {
		this.italianColor = italianColor;
	}

	public String getCodeColor() {
		return codeColor;
	}

	public void setCodeColor(String codeColor) {
		this.codeColor = codeColor;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public static Map<Integer, BacklogColoreEnum> getPriorityMap() {
		return priorityMap;
	}

	public static BacklogColoreEnum getEnum(String color) {
		BacklogColoreEnum toReturn = null;
		if (map.containsKey(color)) {
			toReturn = map.get(color);
		}
		return toReturn;
	}

}
