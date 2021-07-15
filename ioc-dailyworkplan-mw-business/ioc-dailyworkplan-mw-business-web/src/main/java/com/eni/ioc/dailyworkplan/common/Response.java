package com.eni.ioc.dailyworkplan.common;

public class Response<T> {
	private ResultResponse result;
	private T data;

	public ResultResponse getResult() {
		if (result == null) {
			result = new ResultResponse();
		}
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

}
