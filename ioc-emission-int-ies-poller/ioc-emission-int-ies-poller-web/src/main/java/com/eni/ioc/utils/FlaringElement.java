package com.eni.ioc.utils;

public enum FlaringElement {
	E13,
	E14,
	E15;
	
	public static boolean isElement(String name) {
		boolean match = false;
		for (FlaringElement e: FlaringElement.values()) {
			if (e.name().equals(name)) {
				match = true;
				break;
			}
		}
		return match;
	}
}
