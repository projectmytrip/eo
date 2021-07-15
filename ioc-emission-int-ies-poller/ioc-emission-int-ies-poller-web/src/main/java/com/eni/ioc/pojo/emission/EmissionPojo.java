package com.eni.ioc.pojo.emission;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "at",
    "data"
})
public class EmissionPojo implements Serializable {

	private static final long serialVersionUID = 4576157511766309359L;
	
	@JsonProperty("at")
	private long at;
	
	@JsonProperty("data")
	private EmissionData data;

	public EmissionPojo() {
	}
	
	public EmissionPojo(long at, EmissionData data) {
		super();
		this.at = at;
		this.data = data;
	}

	@JsonProperty("at")
	public long getAt() {
		return at;
	}

	@JsonProperty("at")
	public void setAt(long at) {
		this.at = at;
	}

	@JsonProperty("data")
	public EmissionData getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(EmissionData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "[at=" + at + ", data=" + data + "]";
	}

}
