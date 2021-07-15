package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntegrityOperatingWindowsDto {

	@JsonProperty("ZoneName")
	private String zoneName;

	@JsonProperty("N")
	private IntegrityOperatingWindowsCount n = new IntegrityOperatingWindowsCount();

	@JsonProperty("PIVisionURL")
	private String PIVisionURL;

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public IntegrityOperatingWindowsCount getN() {
		return n;
	}

	public void setN(IntegrityOperatingWindowsCount n) {
		this.n = n;
	}

	public String getPIVisionURL() {
		return PIVisionURL;
	}

	public void setPIVisionURL(String PIVisionURL) {
		this.PIVisionURL = PIVisionURL;
	}
}

