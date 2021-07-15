package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RicercaFilterDto implements Serializable {
	private final static long serialVersionUID = 4620379510248555928L;

	// Fields
	@JsonProperty("societyList")
	private List<String> societyList;
	
	// Constructor

	public RicercaFilterDto() {
		
	}

	public RicercaFilterDto(List<String> societyList) {
		this.setSocietyList(societyList);
	}

	public List<String> getSocietyList() {
		return societyList;
	}

	public void setSocietyList(List<String> societyList) {
		this.societyList = societyList;
	}

	

}
