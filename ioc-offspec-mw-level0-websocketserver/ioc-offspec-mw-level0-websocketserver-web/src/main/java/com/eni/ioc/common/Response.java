package com.eni.ioc.common;

public class Response<T> {
	private ResultResponse result;
	private T data;
	private String event;
	private String keyname;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public ResultResponse getResult() {
		if (result == null)
			result = new ResultResponse();
		return result;
	}

	public void setResult(ResultResponse result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	
	

}
