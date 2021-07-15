package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;

public class ReasonMoCInput implements Serializable {

	private static final long serialVersionUID = -7955049034398776772L;

	private String id;
	private String label;
	private String value;

	public ReasonMoCInput() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ReasonMoCInput [id=" + id + ", value=" + value + "]";
	}

}
