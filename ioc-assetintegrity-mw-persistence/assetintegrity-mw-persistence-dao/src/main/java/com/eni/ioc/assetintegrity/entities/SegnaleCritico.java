package com.eni.ioc.assetintegrity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SEGNALI CRITICI")
@Entity
public class SegnaleCritico {

	@Column(name = "Area")
	private String area;

	@Column(name = "LastStatusChangeDate")
	private String lastStatusChangeDate;

	@Column(name = "Value")
	private String value;

	@Column(name = "Category")
	private String category;

	@Id
	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@Column(name = "BlockInput")
	private String blockInput;

	@Column(name = "BlockInput Status")
	private String blockInputStatus;

	@Column(name = "Severity")
	private String severity;

	@Column(name = "Asset")
	private String asset;

	public SegnaleCritico() {

	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}

	public void setLastStatusChangeDate(String lastStatusChangeDate) {
		this.lastStatusChangeDate = lastStatusChangeDate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBlockInput() {
		return blockInput;
	}

	public void setBlockInput(String blockInput) {
		this.blockInput = blockInput;
	}

	public String getBlockInputStatus() {
		return blockInputStatus;
	}

	public void setBlockInputStatus(String blockInputStatus) {
		this.blockInputStatus = blockInputStatus;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

}
