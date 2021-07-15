package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;

public class CreaManualWorkflowInput implements Serializable {

	private static final long serialVersionUID = 2783187971965704533L;
	
	private String asset;
	private String vista;
	private String level;
	private String code;
	private Object manualRequest;
	
	public CreaManualWorkflowInput() {
	
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	public String getVista() {
		return vista;
	}
	public void setVista(String vista) {
		this.vista = vista;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getManualRequest() {
		return this.manualRequest;
	}
	public void setManualRequest(Object manualRequest) {
		this.manualRequest = manualRequest;
	}
	
}
