package com.eni.ioc.pojo.flaring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "at",
    "data"
})
public class FlaringPojo implements Serializable {

	private static final long serialVersionUID = 4576157511766309359L;
	
	@JsonProperty("at")
	private long at;
	
	@JsonProperty("data")
	private FlaringData data;

	public FlaringPojo() {
	}
	
	public FlaringPojo(long at, FlaringData data) {
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
	public FlaringData getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(FlaringData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "[at=" + at + ", data=" + data + "]";
	}

}
