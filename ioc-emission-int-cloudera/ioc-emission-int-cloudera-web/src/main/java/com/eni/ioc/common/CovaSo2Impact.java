package com.eni.ioc.common;

import java.util.Date;

public class CovaSo2Impact {
	
	private Date datetime;
	private String feature;
	private Float impact;
	private Float median;
	private Float value;
	private String descriptor;
	private String engunits;

	public CovaSo2Impact() {
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Float getImpact() {
		return impact;
	}

	public void setImpact(Float impact) {
		this.impact = impact;
	}

	public Float getMedian() {
		return median;
	}

	public void setMedian(Float median) {
		this.median = median;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public String getEngunits() {
		return engunits;
	}

	public void setEngunits(String engunits) {
		this.engunits = engunits;
	}
}
