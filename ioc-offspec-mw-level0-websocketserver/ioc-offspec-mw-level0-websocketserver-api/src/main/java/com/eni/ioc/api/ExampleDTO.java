package com.eni.ioc.api;

import java.io.Serializable;

public class ExampleDTO implements Serializable {

	private static final long serialVersionUID = 10000000L;

	private int id;
	private String desc;

	public ExampleDTO(){
	}

	public ExampleDTO(int id, String desc){
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
