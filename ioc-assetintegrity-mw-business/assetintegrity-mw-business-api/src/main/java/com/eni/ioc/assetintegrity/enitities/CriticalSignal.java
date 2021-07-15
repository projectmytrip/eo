package com.eni.ioc.assetintegrity.enitities;

import java.io.Serializable;

public class CriticalSignal implements Serializable {

	private String area;
	private String blockInputDate;
	private String value;
	private String category;
	private String name;
	private String description;
	private String blockInput;
	private String blockInputStatus;
	private String severity;
	private String asset;
	private String lastStatusChangeDate;
	
	public CriticalSignal() {
		// Auto-generated constructor stub
	}

	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getBlockInput() {
		return blockInput;
	}
	
	public void setBlockInput(String blockInput) {
		this.blockInput = blockInput;
	}
	
	public String getBlockInputDate() {
		return blockInputDate;
	}
	
	public void setBlockInputDate(String blockInputDate) {
		this.blockInputDate = blockInputDate;
	}
	
	public String getBlockInputStatus() {
		return blockInputStatus;
	}
	
	public void setBlockInputStatus(String blockInputStatus) {
		this.blockInputStatus = blockInputStatus;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getAsset() {
		return asset;
	}
	
	public String getSeverity() {
		return severity;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "CriticalSignal [area=" + area + ", blockInputDate=" + blockInputDate + ", lastStatusChangeDate=" + lastStatusChangeDate + ", value=" + value
				+ ", category=" + category + ", name=" + name + ", description=" + description + ", blockInput="
				+ blockInput + ", blockInputStatus=" + blockInputStatus + ", severity=" + severity + ", asset=" + asset
				+ "]";
	}
	
	public String toStringShort() {
		return "[area=" + area + ", blockInputDate=" + blockInputDate + ", name=" + name + ", description=" + ", severity=" + severity + "]";
	}

	public String getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}

	public void setLastStatusChangeDate(String lastStatusChangeDate) {
		this.lastStatusChangeDate = lastStatusChangeDate;
	}
	
}
