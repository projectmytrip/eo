package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMailDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String uid;
	private String email;
	private Integer action;
	private boolean isCarbonCopy;


	public UserMailDto() {
		super();
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public boolean isCarbonCopy() {
		return isCarbonCopy;
	}

	public void setCarbonCopy(boolean carbonCopy) {
		isCarbonCopy = carbonCopy;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof UserMailDto)){
			return false;
		}
		UserMailDto o2 = (UserMailDto) obj;
		
		return this.getUid().equalsIgnoreCase(o2.getUid());

	}
	
	@Override
	public int hashCode() {
		return this.uid.hashCode();
	}

}
