package com.eni.ioc.assetintegrity.enitities;

import java.io.Serializable;

public class AssetIntegrityPointDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contentValue;
	private String tsValue;
	private boolean validData;
	
	public AssetIntegrityPointDto() {
	}
	
	public AssetIntegrityPointDto(String contentValue) {
		this.setContentValue(contentValue);
	}

	public AssetIntegrityPointDto(String contentValue, boolean validData) {
		this.setContentValue(contentValue);
		this.setValidData(validData);
	}
	
	public AssetIntegrityPointDto(String contentValue, String timeStampValue, boolean validData) {
		this.setContentValue(contentValue);
		this.setTsValue(timeStampValue);
		this.setValidData(validData);
	}

	public String getContentValue() {
		return contentValue;
	}

	public void setContentValue(String contentValue) {
		this.contentValue = contentValue;
	}

	public String getTsValue() {
		return tsValue;
	}

	public void setTsValue(String timeStampValue) {
		this.tsValue = timeStampValue;
	}

	public boolean isValidData() {
		return validData;
	}

	public void setValidData(boolean validData) {
		this.validData = validData;
	}
	
	@Override
	public String toString() {
		return	"contentValue: " + contentValue + ", tsValue: " + tsValue;
	}

}
