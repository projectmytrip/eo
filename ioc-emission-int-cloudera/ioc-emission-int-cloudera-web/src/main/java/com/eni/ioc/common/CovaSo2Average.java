package com.eni.ioc.common;

import java.util.Date;

public class CovaSo2Average {
	
	private Date datetime;
	private Float mape;
	private Float p;
	private Float prediction;

	public CovaSo2Average() {
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Float getMape() {
		return mape;
	}

	public void setMape(Float mape) {
		this.mape = mape;
	}

	public Float getP() {
		return p;
	}

	public void setP(Float p) {
		this.p = p;
	}

	public Float getPrediction() {
		return prediction;
	}

	public void setPrediction(Float prediction) {
		this.prediction = prediction;
	}	
}
