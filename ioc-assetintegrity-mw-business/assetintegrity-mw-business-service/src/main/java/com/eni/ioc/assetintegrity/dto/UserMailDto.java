package com.eni.ioc.assetintegrity.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uid;
	private String email;
	private Integer action;
	private boolean isCarbonCopy;

	public UserMailDto() {
		super();
	}

	public UserMailDto(String uid, String email, Integer action, boolean isCarbonCopy) {
		this.uid = uid;
		this.email = email;
		this.action = action;
		this.isCarbonCopy = isCarbonCopy;
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

	public boolean getCarbonCopy() {
		return isCarbonCopy;
	}

	public void setCarbonCopy(boolean carbonCopy) {
		isCarbonCopy = carbonCopy;
	}

	@Override
	public String toString() {
		return "UserMailDto{" + "uid='" + uid + '\'' + ", email='" + email + '\'' + ", action=" + action +
				", isCarbonCopy=" + isCarbonCopy + '}';
	}
}
