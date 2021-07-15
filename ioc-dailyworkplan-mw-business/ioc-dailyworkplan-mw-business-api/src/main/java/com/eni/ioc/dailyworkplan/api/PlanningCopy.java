package com.eni.ioc.dailyworkplan.api;

import java.io.Serializable;

public class PlanningCopy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324359854322778126L;
	private String sourceDate;
	private String destinationDate;
	private String insertionBy;

	public String getSourceDate() {
		return sourceDate;
	}

	public void setSourceDate(String sourceDate) {
		this.sourceDate = sourceDate;
	}

	public String getDestinationDate() {
		return destinationDate;
	}

	public void setDestinationDate(String destinationDate) {
		this.destinationDate = destinationDate;
	}

	public String getInsertionBy() {
		return insertionBy;
	}

	public void setInsertionBy(String insertionBy) {
		this.insertionBy = insertionBy;
	}
		

}
