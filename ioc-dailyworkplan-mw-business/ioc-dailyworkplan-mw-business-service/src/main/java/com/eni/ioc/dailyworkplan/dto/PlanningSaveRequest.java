package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanningSaveRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -109366421870021175L;
	// Fields
	@JsonProperty("planning")
	private PlanningDto planning;

	// Constructor

	public PlanningSaveRequest() {
	}

	@JsonProperty("planning")
	public PlanningDto getPlanning() {
		return planning;
	}

	@JsonProperty("planning")
	public void setPlanning(PlanningDto planning) {
		this.planning = planning;
	}

}
