package com.eni.ioc.common;

import java.util.Date;

public class CovaSo2Prediction {

	private Date datetime;
	private Float proba;
	private String keyName;

	public CovaSo2Prediction() {
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Float getProba() {
		return proba;
	}

	public void setProba(Float proba) {
		this.proba = proba;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	
}
