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
	
	@Column(name = "MoC")
	private Integer moc;
	
	@Column(name = "UID_UPDATE_MOC")
	private String uidUpdateMoc;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_MOC")
	private Date lastUpdateMoc;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ATTIVAZIONE")
	private Date dataAttivazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_DISATTIVAZIONE")
	private Date dataDisattivazione;

	
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

	public Integer getMoc() {
		return moc;
	}

	public void setMoc(Integer moc) {
		this.moc = moc;
	}

	public Date getDataAttivazione() {
		return dataAttivazione;
	}

	public void setDataAttivazione(Date dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}

	public Date getDataDisattivazione() {
		return dataDisattivazione;
	}

	public void setDataDisattivazione(Date dataDisattivazione) {
		this.dataDisattivazione = dataDisattivazione;
	}

	public String getUidUpdateMoc() {
		return uidUpdateMoc;
	}

	public void setUidUpdateMoc(String uidUpdateMoc) {
		this.uidUpdateMoc = uidUpdateMoc;
	}

	public Date getLastUpdateMoc() {
		return lastUpdateMoc;
	}

	public void setLastUpdateMoc(Date lastUpdateMoc) {
		this.lastUpdateMoc = lastUpdateMoc;
	}
}