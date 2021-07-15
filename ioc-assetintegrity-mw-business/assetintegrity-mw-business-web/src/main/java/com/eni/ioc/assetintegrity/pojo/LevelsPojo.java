package com.eni.ioc.assetintegrity.pojo;

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
public class LevelsPojo implements Serializable {

	private static final long serialVersionUID = 323042350724888029L;

	@JsonProperty("result")
	private Result result;
	@JsonProperty("data")
	private List<String> data;

	public LevelsPojo() {
	}
	
	public LevelsPojo(Result result, List<String> data) {
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
	public List<String> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "LevelsPojo [result=" + result + ", data=" + data + "]";
	}

}
