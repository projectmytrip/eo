package com.eni.ioc.common;

import java.util.Date;

public class CovaFlaringEvent {
	
	private String id;
	private Float avgEmission;
	private Date startEvent;
	private Date endEvent;
	private Integer eventLength;
	private Float maxEmission;
	private Float totalEmission;
	private String keyName;

	public CovaFlaringEvent() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getAvgEmission() {
		return avgEmission;
	}

	public void setAvgEmission(Float avgEmission) {
		this.avgEmission = avgEmission;
	}

	public Date getStartEvent() {
		return startEvent;
	}

	public void setStartEvent(Date startEvent) {
		this.startEvent = startEvent;
	}

	public Date getEndEvent() {
		return endEvent;
	}

	public void setEndEvent(Date endEvent) {
		this.endEvent = endEvent;
	}

	public Integer getEventLength() {
		return eventLength;
	}

	public void setEventLength(Integer eventLength) {
		this.eventLength = eventLength;
	}

	public Float getMaxEmission() {
		return maxEmission;
	}

	public void setMaxEmission(Float maxEmission) {
		this.maxEmission = maxEmission;
	}

	public Float getTotalEmission() {
		return totalEmission;
	}

	public void setTotalEmission(Float totalEmission) {
		this.totalEmission = totalEmission;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}	
	
	
}
