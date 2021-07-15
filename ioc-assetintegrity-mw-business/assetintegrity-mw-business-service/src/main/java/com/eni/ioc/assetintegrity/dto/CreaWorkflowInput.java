package com.eni.ioc.assetintegrity.dto;

import java.io.Serializable;

public class CreaWorkflowInput implements Serializable{

	private static final long serialVersionUID = 2783187971965704533L;
	
	private String asset;
	private String vista;
	private String level;
	private String code;
	private String idCondizioneAnomala;
	private CondizioneAnomala condizioneAnomala;
	
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
	public String getIdCondizioneAnomala() {
		return idCondizioneAnomala;
	}
	public void setIdCondizioneAnomala(String idCondizioneAnomala) {
		this.idCondizioneAnomala = idCondizioneAnomala;
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
	public CondizioneAnomala getCondizioneAnomala() {
		return condizioneAnomala;
	}
	public void setCondizioneAnomala(CondizioneAnomala condizioneAnomala) {
		this.condizioneAnomala = condizioneAnomala;
	}
	

}
