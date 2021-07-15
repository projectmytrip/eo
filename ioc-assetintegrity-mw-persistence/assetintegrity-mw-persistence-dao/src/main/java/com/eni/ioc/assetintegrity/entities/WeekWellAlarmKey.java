package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;



public class WeekWellAlarmKey implements Serializable {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 4950591521625739590L;
	
	private String wellCode;
	private String currQuarter;
	public String getWellCode() {
		return wellCode;
	}
	public void setWellCode(String wellCode) {
		this.wellCode = wellCode;
	}
	public String getCurrQuarter() {
		return currQuarter;
	}
	public void setCurrQuarter(String currQuarter) {
		this.currQuarter = currQuarter;
	}

}
