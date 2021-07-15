package com.eni.ioc.pojo.emission;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "E03", "E04", "E04BIS", "E04RISEXX", "E11A", "E11B", "E11C", "E12A", "E12B", "E12C", "E12D", "E20",
		"EXX" })
public class EmissionData implements Serializable {

	private static final long serialVersionUID = 7828773623915992440L;

	@JsonProperty("E20")
	private Station e20;

	@JsonProperty("EXX")
	private Station exx;

	@JsonProperty("E04RISEXX")
	private Station e04risexx;

	@JsonProperty("E04")
	private Station e04;

	@JsonProperty("E04BIS")
	private Station e04bis;

	@JsonProperty("E03")
	private Station e03;

	@JsonProperty("E11A")
	private Station e11a;

	@JsonProperty("E12B")
	private Station e12b;

	@JsonProperty("E11B")
	private Station e11b;

	@JsonProperty("E12A")
	private Station e12a;

	@JsonProperty("E12C")
	private Station e12c;

	@JsonProperty("E11C")
	private Station e11c;

	@JsonProperty("E12D")
	private Station e12d;

	public EmissionData() {
	}

	public EmissionData(Station e20, Station exx, Station e04risexx, Station e04, Station e04bis, Station e03,
			Station e11a, Station e12b, Station e11b, Station e12a, Station e12c, Station e11c, Station e12d) {
		super();
		this.e20 = e20;
		this.exx = exx;
		this.e04risexx = e04risexx;
		this.e04 = e04;
		this.e04bis = e04bis;
		this.e03 = e03;
		this.e11a = e11a;
		this.e12b = e12b;
		this.e11b = e11b;
		this.e12a = e12a;
		this.e12c = e12c;
		this.e11c = e11c;
		this.e12d = e12d;
	}

	@JsonProperty("E20")
	public Station getE20() {
		return e20;
	}

	@JsonProperty("E20")
	public void setE20(Station e20) {
		this.e20 = e20;
	}

	@JsonProperty("EXX")
	public Station getExx() {
		return exx;
	}

	@JsonProperty("EXX")
	public void setExx(Station exx) {
		this.exx = exx;
	}

	@JsonProperty("E04RISEXX")
	public Station getE04risexx() {
		return e04risexx;
	}

	@JsonProperty("E04RISEXX")
	public void setE04risexx(Station e04risexx) {
		this.e04risexx = e04risexx;
	}

	@JsonProperty("E04")
	public Station getE04() {
		return e04;
	}

	@JsonProperty("E04")
	public void setE04(Station e04) {
		this.e04 = e04;
	}

	@JsonProperty("E04BIS")
	public Station getE04bis() {
		return e04bis;
	}

	@JsonProperty("E04BIS")
	public void setE04bis(Station e04bis) {
		this.e04bis = e04bis;
	}

	@JsonProperty("E03")
	public Station getE03() {
		return e03;
	}

	@JsonProperty("E03")
	public void setE03(Station e03) {
		this.e03 = e03;
	}

	@JsonProperty("E11A")
	public Station getE11a() {
		return e11a;
	}

	@JsonProperty("E11A")
	public void setE11a(Station e11a) {
		this.e11a = e11a;
	}

	@JsonProperty("E12B")
	public Station getE12b() {
		return e12b;
	}

	@JsonProperty("E12B")
	public void setE12b(Station e12b) {
		this.e12b = e12b;
	}

	@JsonProperty("E11B")
	public Station getE11b() {
		return e11b;
	}

	@JsonProperty("E11B")
	public void setE11b(Station e11b) {
		this.e11b = e11b;
	}

	@JsonProperty("E12A")
	public Station getE12a() {
		return e12a;
	}

	@JsonProperty("E12A")
	public void setE12a(Station e12a) {
		this.e12a = e12a;
	}

	@JsonProperty("E12C")
	public Station getE12c() {
		return e12c;
	}

	@JsonProperty("E12C")
	public void setE12c(Station e12c) {
		this.e12c = e12c;
	}

	@JsonProperty("E11C")
	public Station getE11c() {
		return e11c;
	}

	@JsonProperty("E11C")
	public void setE11c(Station e11c) {
		this.e11c = e11c;
	}

	@JsonProperty("E12D")
	public Station getE12d() {
		return e12d;
	}

	@JsonProperty("E12D")
	public void setE12d(Station e12d) {
		this.e12d = e12d;
	}

	@Override
	public String toString() {
		return "[E20=" + e20 + ", EXX=" + exx + ", E04RISEXX=" + e04risexx + ", E04=" + e04 + ", E04BIS="
				+ e04bis + ", E03=" + e03 + ", E11A=" + e11a + ", E12B=" + e12b + ", E11B=" + e11b + ", E12A=" + e12a
				+ ", E12C=" + e12c + ", E11C=" + e11c + ", E12D=" + e12d + "]";
	}

}
