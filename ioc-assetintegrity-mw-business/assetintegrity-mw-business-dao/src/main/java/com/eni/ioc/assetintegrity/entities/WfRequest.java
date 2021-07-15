package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WF_REQUEST")
public class WfRequest  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SIGNALS")
	private String signals;

	@Column(name = "ASSET")
	private String asset;

	@Column(name = "DURATION")
	private String duration;

	@Column(name = "EMERGENCY")
	private String emergency;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "REQUEST_TYPE")
	private String requestType;
	
	@Column(name = "SAP")
	private String sap;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;
	
	public WfRequest() {
		// Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getSignals() {
		return signals;
	}

	public void setSignals(String signals) {
		this.signals = signals;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEmergency() {
		return emergency;
	}

	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getSap() {
		return sap;
	}

	public void setSap(String sap) {
		this.sap = sap;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
}
