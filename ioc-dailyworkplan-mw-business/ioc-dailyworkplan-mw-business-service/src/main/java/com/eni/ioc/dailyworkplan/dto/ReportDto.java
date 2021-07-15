package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDto implements Serializable {
	private final static long serialVersionUID = 4620379510248555928L;

	// Fields
	@JsonProperty("chartData")
	Map<Integer, Map<String,String>> chartData;
	
	
	// Constructor

	public ReportDto() {

	}

	public ReportDto(Map<Integer, Map<String,String>> chartData) {
		this.setChartData(chartData);
	}

	@JsonProperty("chartData")
	public Map<Integer, Map<String,String>> getChartData() {
		return chartData;
	}

	@JsonProperty("chartData")
	public void setChartData(Map<Integer, Map<String,String>> chartData) {
		this.chartData = chartData;
	}
	
}
