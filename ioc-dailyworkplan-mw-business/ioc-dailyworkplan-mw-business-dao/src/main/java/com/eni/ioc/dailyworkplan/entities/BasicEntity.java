package com.eni.ioc.dailyworkplan.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasicEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9005069678213642649L;
	
	@Column(name = "ASSET")
	private String asset;
	
	@Column(name = "FLG_EXIST")
	private String flgExist;
	
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;
	
	public abstract Long getId();

	public String getFlgExist() {
		return flgExist;
	}

	public void setFlgExist(String flgExist) {
		this.flgExist = flgExist;
	}
	
	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

}