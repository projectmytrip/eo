package com.eni.ioc.assetintegrity.dto;

import java.io.Serializable;
import java.util.List;

import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;

public class CheckSignalsWF implements Serializable{

	private static final long serialVersionUID = 2783187971965704533L;
	
	private String asset;
	private Boolean isOver;
	private List<CriticalSignalDto> signals;
	
	public List<CriticalSignalDto> getSignals() {
		return signals;
	}
	public void setSignals(List<CriticalSignalDto> signals) {
		this.signals = signals;
	}
	public Boolean getIsOver() {
		return isOver;
	}
	public void setIsOver(Boolean isOver) {
		this.isOver = isOver;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	
	

}
