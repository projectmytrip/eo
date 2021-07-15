package com.eni.ioc.dailyworkplan.service.utils;

import java.io.Serializable;
import java.util.List;

import com.eni.ioc.dailyworkplan.dto.UserMailDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "result", "data" })
public class RolesPojo implements Serializable {

	private static final long serialVersionUID = 323042350724888029L;

	@JsonProperty("result")
	private Result result;
	@JsonProperty("data")
	private List<UserMailDto> data;

	public RolesPojo() {
	}

	public RolesPojo(Result result, List<UserMailDto> data) {
		super();
		this.result = result;
		this.data = data;
	}

	@JsonProperty("result")
	public Result getResult() {
		return result;
	}

	@JsonProperty("result")
	public void setResult(Result result) {
		this.result = result;
	}

	@JsonProperty("data")
	public List<UserMailDto> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<UserMailDto> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RolesPojo [result=" + result + ", data=" + data + "]";
	}

}
