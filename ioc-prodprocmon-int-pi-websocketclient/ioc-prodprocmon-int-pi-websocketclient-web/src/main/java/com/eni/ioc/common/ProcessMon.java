package com.eni.ioc.common;

public class ProcessMon {

	private String value;
	private String url;
	private String datetime;
	
	public ProcessMon() {
		super();
	}
	
	public ProcessMon(String value, String url, String datetime) {
		super();
		this.value = value;
		this.url = url;
		this.datetime = datetime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "ProcessMon [value=" + value + ", url=" + url + ", datetime=" + datetime + "]";
	}
}
