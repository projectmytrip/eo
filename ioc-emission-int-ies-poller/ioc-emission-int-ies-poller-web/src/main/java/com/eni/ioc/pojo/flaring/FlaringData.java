package com.eni.ioc.pojo.flaring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "E13", "E14", "E15"})
public class FlaringData implements Serializable {

	private static final long serialVersionUID = 7828773623915992440L;

	@JsonProperty("E13")
	private Station e13;

	@JsonProperty("E14")
	private Station e14;

	@JsonProperty("E15")
	private Station e15;

	public FlaringData() {
	}

	public FlaringData(Station e13, Station e14, Station e15) {
		super();
		this.e13 = e13;
		this.e14 = e14;
		this.e15 = e15;
	}

	@JsonProperty("E13")
	public Station getE13() {
		return e13;
	}

	@JsonProperty("E13")
	public void setE13(Station e13) {
		this.e13 = e13;
	}

	@JsonProperty("E14")
	public Station getE14() {
		return e14;
	}

	@JsonProperty("E14")
	public void setE14(Station e14) {
		this.e14 = e14;
	}

	@JsonProperty("E15")
	public Station getE15() {
		return e15;
	}

	@JsonProperty("E15")
	public void setE15(Station e15) {
		this.e15 = e15;
	}

	@Override
	public String toString() {
		return "FlaringData [e13=" + e13 + ", e14=" + e14 + ", e15=" + e15 + "]";
	}
}
