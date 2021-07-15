package com.eni.ioc.dailyworkplan.api;

import java.io.Serializable;

public class UserFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324359854322778126L;
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
