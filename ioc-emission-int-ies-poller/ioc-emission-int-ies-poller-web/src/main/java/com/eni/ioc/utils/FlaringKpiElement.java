package com.eni.ioc.utils;

public enum FlaringKpiElement {

	PORTATAMASSICA("FLUSSO_MASSA"),
	PORTATAVOLUMETRICA("FLUSSO_VOLUME");
	
	private String realName;
	
	public String getRealName() {
		return this.realName;
	}
	
	private FlaringKpiElement(String realName) {
		this.realName = realName;
	}
}
