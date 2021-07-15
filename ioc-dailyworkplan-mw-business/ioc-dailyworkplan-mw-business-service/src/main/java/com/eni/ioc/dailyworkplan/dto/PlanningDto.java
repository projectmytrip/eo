package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanningDto implements Serializable {
	private final static long serialVersionUID = 4620379510248555928L;

	// Fields
	@JsonProperty("id")
	private Long id;

	@JsonProperty("asset")
	private String asset;

	@JsonProperty("referenceDate")
	private Date referenceDate;

	@JsonProperty("insertionBy")
	private String insertionBy;
	
	@JsonProperty("insertionByUsername")
	private String insertionByUsername;
	
	@JsonProperty("insertionDate")
	private Date insertionDate;

	@JsonProperty("stateCode")
	private String stateCode;

	@JsonProperty("state")
	private String state;
	
	@JsonProperty("activityList")
	private List<ActivityDto> activityList;

	@JsonProperty("formattedReferenceDate")
	private String formattedReferenceDate;
	
	@JsonProperty("formattedInsertionDate")
	private String formattedInsertionDate;
	
	@JsonProperty("lastHistoryEmailNote")
	private String lastHistoryEmailNote;
	
	@JsonProperty("allHistoryEmailNote")
	private List<String> allHistoryEmailNote;

	// Constructor

	public PlanningDto() {
	}

	public PlanningDto(Long id, String asset, Date insertionDate, Date referenceDate, String insertionBy, 
			String insertionByUsername, String stateCode, String state, List<ActivityDto> activityList,
			String lastHistoryEmailNote, List<String> allHistoryEmailNote) {
		this.setId(id);
		this.setAsset(asset);
		this.setInsertionDate(insertionDate);
		this.setReferenceDate(referenceDate);
		this.setInsertionBy(insertionBy);
		this.setInsertionByUsername(insertionByUsername);
		this.setStateCode(stateCode);
		this.setState(state);
		this.setActivityList(activityList);
		if(this.getReferenceDate() != null){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String formattedReferenceDate = df.format(this.getReferenceDate());
			this.setFormattedReferenceDate(formattedReferenceDate);
		}
		if(this.getInsertionDate() != null){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String formattedInsertionDate = df.format(this.getInsertionDate());
			this.setFormattedInsertionDate(formattedInsertionDate);
		}
		this.setLastHistoryEmailNote(lastHistoryEmailNote);
		this.setAllHistoryEmailNote(allHistoryEmailNote);
	}

	@JsonProperty("insertionBy")
	public String getInsertionBy() {
		return insertionBy;
	}

	@JsonProperty("insertionBy")
	public void setInsertionBy(String insertionBy) {
		this.insertionBy = insertionBy;
	}

	@JsonProperty("insertionByUsername")
	public String getInsertionByUsername() {
		return insertionByUsername;
	}

	@JsonProperty("insertionByUsername")
	public void setInsertionByUsername(String insertionByUsername) {
		this.insertionByUsername = insertionByUsername;
	}

	@JsonProperty("stateCode")
	public String getStateCode() {
		return stateCode;
	}

	@JsonProperty("stateCode")
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("asset")
	public String getAsset() {
		return asset;
	}

	@JsonProperty("asset")
	public void setAsset(String asset) {
		this.asset = asset;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("referenceDate")
	public Date getReferenceDate() {
		return referenceDate;
	}

	@JsonProperty("referenceDate")
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	@JsonProperty("activityList")
	public List<ActivityDto> getActivityList() {
		return activityList;
	}

	@JsonProperty("activityList")
	public void setActivityList(List<ActivityDto> activityList) {
		this.activityList = activityList;
	}

	@JsonProperty("insertionDate")
	public Date getInsertionDate() {
		return insertionDate;
	}

	@JsonProperty("insertionDate")
	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	@JsonProperty("formattedReferenceDate")
	public String getFormattedReferenceDate() {
		return formattedReferenceDate;
	}

	@JsonProperty("formattedReferenceDate")
	public void setFormattedReferenceDate(String formattedReferenceDate) {
		this.formattedReferenceDate = formattedReferenceDate;
	}

	@JsonProperty("formattedInsertionDate")
	public String getFormattedInsertionDate() {
		return formattedInsertionDate;
	}

	@JsonProperty("formattedInsertionDate")
	public void setFormattedInsertionDate(String formattedInsertionDate) {
		this.formattedInsertionDate = formattedInsertionDate;
	}

	@JsonProperty("lastHistoryEmailNote")
	public String getLastHistoryEmailNote() {
		return lastHistoryEmailNote;
	}

	@JsonProperty("lastHistoryEmailNote")
	public void setLastHistoryEmailNote(String lastHistoryEmailNote) {
		this.lastHistoryEmailNote = lastHistoryEmailNote;
	}

	@JsonProperty("allHistoryEmailNote")
	public List<String> getAllHistoryEmailNote() {
		return allHistoryEmailNote;
	}

	@JsonProperty("allHistoryEmailNote")
	public void setAllHistoryEmailNote(List<String> allHistoryEmailNote) {
		this.allHistoryEmailNote = allHistoryEmailNote;
	}

}
