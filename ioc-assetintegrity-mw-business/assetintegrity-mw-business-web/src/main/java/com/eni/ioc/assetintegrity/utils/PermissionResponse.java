package com.eni.ioc.assetintegrity.utils;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "result",
    "data"
})
public class PermissionResponse implements Serializable {

	private static final long serialVersionUID = 323042350724888029L;

	@JsonProperty("result")
	private Result result;
	@JsonProperty("data")
	private List<UserPermissionDto> data;

	public PermissionResponse() {
	}
	
	public PermissionResponse(Result result, List<UserPermissionDto> data) {
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
	public List<UserPermissionDto> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<UserPermissionDto> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "LevelsPojo [result=" + result + ", data=" + data + "]";
	}

}
