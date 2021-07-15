package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.eni.ioc.assetintegrity.enitities.CriticalSignal;

public class OpenWorkflowCriticalSignalsInput implements Serializable {

	private List<CriticalSignal> signals;
	private String notes;
	private String duration;
	private String requestType;
	private String emergency;
	private String sap;
	private Date startDate;
    
    public OpenWorkflowCriticalSignalsInput() {
		// Auto-generated constructor stub
	}
        
    public OpenWorkflowCriticalSignalsInput(List<CriticalSignal> signals, String duration, String emergency, String requestType, String notes, Date startDate, String sap) {
		super();
		this.signals = signals;
		this.duration = duration;
		this.requestType = requestType;
		this.emergency = emergency;
		this.notes = notes;
		this.startDate = startDate;
		this.sap = sap;
	}

	public List<CriticalSignal> getSignals() {
		return signals;
	}
    
	public void setSignals(List<CriticalSignal> signals) {
		this.signals = signals;
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
	
	public String getNotes() {
		return notes;
	}
    
    public void setNotes(String notes) {
		this.notes = notes;
	}
    
    public Date getStartDate() {
		return startDate;
	}
    
    public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
    
}

