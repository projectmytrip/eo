package com.eni.ioc.common;

import java.util.Date;

public class CovaFlaringAD {
	
	private Date sAlarm;
	private Date eAlarm;
	private String tag;
	private String description;
	private String unit;
	private Float from;
	private Float to;
	private String importance;
	private String keyName;
	private String measureUnit;

	public CovaFlaringAD() {
	}


	public Date getsAlarm() {
		return sAlarm;
	}


	public void setsAlarm(Date sAlarm) {
		this.sAlarm = sAlarm;
	}


	public Date geteAlarm() {
		return eAlarm;
	}


	public void seteAlarm(Date eAlarm) {
		this.eAlarm = eAlarm;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public Float getFrom() {
		return from;
	}


	public void setFrom(Float from) {
		this.from = from;
	}


	public Float getTo() {
		return to;
	}


	public void setTo(Float to) {
		this.to = to;
	}


	public String getImportance() {
		return importance;
	}


	public void setImportance(String importance) {
		this.importance = importance;
	}


	public String getKeyName() {
		return keyName;
	}


	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}


	public String getMeasureUnit() {
		return measureUnit;
	}


	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}


	@Override
	public String toString() {
		return "CovaFlaringAD [sAlarm=" + sAlarm + ", eAlarm=" + eAlarm + ", tag=" + tag + ", description="
				+ description + ", unit=" + unit + ", from=" + from + ", to=" + to + ", importance=" + importance
				+ ", keyName=" + keyName + ", measureUnit=" + measureUnit + "]";
	}
	
	

	
}
