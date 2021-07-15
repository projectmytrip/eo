package com.eni.ioc.assetintegrity.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "KPI_RESERVOIR")
public class KpiReservoir implements Comparable<KpiReservoir> {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@Column(name = "TOOLTIP_MSG")
	private String tooltipMsg;
	
	@Column(name = "FREQ_REGISTRAZIONE")
	private String freqRegistrazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;
	
	@Column(name = "ASSET")
	private String asset;
	
	public KpiReservoir() {
		super();
	}

	public KpiReservoir(String id, String descrizione, String tooltipMsg, String freqRegistrazione, Date insertionDate,
			String asset) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.tooltipMsg = tooltipMsg;
		this.freqRegistrazione = freqRegistrazione;
		this.insertionDate = insertionDate;
		this.asset = asset;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltipMsg() {
		return tooltipMsg;
	}

	public void setTooltipMsg(String tooltipMsg) {
		this.tooltipMsg = tooltipMsg;
	}

	public String getFreqRegistrazione() {
		return freqRegistrazione;
	}

	public void setFreqRegistrazione(String freqRegistrazione) {
		this.freqRegistrazione = freqRegistrazione;
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
	
	@Override
	public String toString() {
		return "KpiReservoir [id=" + id + ", descrizione=" + descrizione + ", tooltipMsg=" + tooltipMsg
				+ ", freqRegistrazione=" + freqRegistrazione + ", insertionDate=" + insertionDate + ", asset=" + asset
				+ "]";
	}

	@Override
	public int compareTo(KpiReservoir o) {
		return this.getInsertionDate().compareTo(o.getInsertionDate());
	}
}
