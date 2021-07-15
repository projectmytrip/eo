package com.eni.ioc.assetintegrity.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "SEGNALI_WF")
public class CriticalSignalWF {

	@Column(name = "NAME")
	private String name;

	@Column(name = "WF_ID")
	private String wfId;

	@Column(name = "APPROVATO")
	private Integer approved;

	@Column(name = "CHIUSO")
	private Integer chiuso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INIZIO")
	private Date dataInizio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_FINE")
	private Date dataFine;
	
	public CriticalSignalWF() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public Integer getApproved() {
		return approved;
	}

	public void setApproved(Integer approved) {
		this.approved = approved;
	}

	public Integer getChiuso() {
		return chiuso;
	}

	public void setChiuso(Integer chiuso) {
		this.chiuso = chiuso;
	}
	
	public Date getDataFine() {
		return dataFine;
	}
	
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	public Date getDataInizio() {
		return dataInizio;
	}
	
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

}
