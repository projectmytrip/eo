package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6123027901470876934L;

	// Fields
	@JsonProperty("hour0614")
	private Long hour0614;

	@JsonProperty("hour1422")
	private Long hour1422;

	@JsonProperty("priority0614")
	private Long priority0614;

	@JsonProperty("priority1422")
	private Long priority1422;

	// Constructor

	public CardInfoDto() {
		this.hour0614 = new Long(0);
		this.hour1422 = new Long(0);
		this.priority0614 = new Long(0);
		this.priority1422 = new Long(0);
	}

	@JsonProperty("hour0614")
	public Long getHour0614() {
		return hour0614;
	}

	@JsonProperty("hour0614")
	public void setHour0614(Long hour0614) {
		this.hour0614 = hour0614;
	}

	@JsonProperty("hour1422")
	public Long getHour1422() {
		return hour1422;
	}

	@JsonProperty("hour1422")
	public void setHour1422(Long hour1422) {
		this.hour1422 = hour1422;
	}

	@JsonProperty("priority0614")
	public Long getPriority0614() {
		return priority0614;
	}

	@JsonProperty("priority0614")
	public void setPriority0614(Long priority0614) {
		this.priority0614 = priority0614;
	}

	@JsonProperty("priority1422")
	public Long getPriority1422() {
		return priority1422;
	}

	@JsonProperty("priority1422")
	public void setPriority1422(Long priority1422) {
		this.priority1422 = priority1422;
	}

}
