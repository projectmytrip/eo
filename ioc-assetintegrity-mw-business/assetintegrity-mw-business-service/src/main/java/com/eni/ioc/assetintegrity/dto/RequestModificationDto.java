package com.eni.ioc.assetintegrity.dto;

import java.util.Date;
import java.util.List;

import com.eni.ioc.assetintegrity.api.ReasonMoCInput;
import com.eni.ioc.assetintegrity.api.RequestModificationInput;
import com.eni.ioc.assetintegrity.api.Segnale;

public class RequestModificationDto {

	private String title;
	private String number;
	private Date date;
	private List<ReasonMoCInput> reasons;
	private String otherReason;
	private String priority;
	private String type;
	private Date typeDate;
	private String description;
	private String documentation;
	private List<Segnale> signals;
	private String user;
	private String uid;

	public RequestModificationDto() {
		// TODO Auto-generated constructor stub
	}

	public RequestModificationDto(RequestModificationInput input) {
		setTitle(input.getTitle());
		setNumber(input.getNumber());
		setDate(input.getDate());
		setReasons(input.getReasons());

		setOtherReason(input.getOtherReason());
		setPriority(input.getPriority());
		setType(input.getType());
		setTypeDate(input.getTypeDate());
		setDescription(input.getDescription());
		setDocumentation(input.getDocumentation());
		setSignals(input.getSignals());
		setUser(input.getUser());
		setUid(input.getUid());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ReasonMoCInput> getReasons() {
		return reasons;
	}

	public void setReasons(List<ReasonMoCInput> reasons) {
		this.reasons = reasons;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTypeDate() {
		return typeDate;
	}

	public void setTypeDate(Date typeDate) {
		this.typeDate = typeDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public List<Segnale> getSignals() {
		return signals;
	}

	public void setSignals(List<Segnale> signals) {
		this.signals = signals;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
