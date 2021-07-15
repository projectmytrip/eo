package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;

public class Segnale implements Serializable {

	private static final long serialVersionUID = 7343463830530192243L;

	private String name;
	private String description;
	private String area;
	private String type;

	public Segnale() {
	
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String toStringShort() {
		return "[area=" + area + ", name=" + name + ", description=" + "]";
	}
}
