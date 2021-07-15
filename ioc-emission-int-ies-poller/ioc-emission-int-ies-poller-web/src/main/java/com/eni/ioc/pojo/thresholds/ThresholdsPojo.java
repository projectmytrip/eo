package com.eni.ioc.pojo.thresholds;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "stazione",
    "parameter",
    "um",
    "hourlimit",
    "daylimit"
})
public class ThresholdsPojo implements Serializable {

	private static final long serialVersionUID = 154404151457704361L;

	@JsonProperty("stazione")
	private String stazione;
	
	@JsonProperty("parameter")
	private String parameter;
	
	@JsonProperty("um")
	private String um;
	
	@JsonProperty("hourlimit")
	private Double hourlimit;
	
	@JsonProperty("daylimit")
	private Double daylimit;

	public ThresholdsPojo() {
	}
	
	public ThresholdsPojo(String stazione, String parameter, String um, Double hourlimit, Double daylimit) {
		super();
		this.stazione = stazione;
		this.parameter = parameter;
		this.um = um;
		this.hourlimit = hourlimit;
		this.daylimit = daylimit;
	}

	@JsonProperty("stazione")
	public String getStazione() {
		return stazione;
	}

	@JsonProperty("stazione")
	public void setStazione(String stazione) {
		this.stazione = stazione;
	}

	@JsonProperty("parameter")
	public String getParameter() {
		return parameter;
	}

	@JsonProperty("parameter")
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@JsonProperty("um")
	public String getUm() {
		return um;
	}

	@JsonProperty("um")
	public void setUm(String um) {
		this.um = um;
	}

	@JsonProperty("hourlimit")
	public Double getHourlimit() {
		return hourlimit;
	}

	@JsonProperty("hourlimit")
	public void setHourlimit(Double hourlimit) {
		this.hourlimit = hourlimit;
	}

	@JsonProperty("daylimit")
	public Double getDaylimit() {
		return daylimit;
	}

	@JsonProperty("daylimit")
	public void setDaylimit(Double daylimit) {
		this.daylimit = daylimit;
	}

	@Override
	public String toString() {
		return "[stazione=" + stazione + ", parameter=" + parameter + ", um=" + um + ", hourlimit="
				+ hourlimit + ", daylimit=" + daylimit + "]";
	}
}
