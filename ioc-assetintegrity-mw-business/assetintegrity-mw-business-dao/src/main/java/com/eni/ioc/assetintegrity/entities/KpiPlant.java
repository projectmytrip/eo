package com.eni.ioc.assetintegrity.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "KPI_PLANT")
public class KpiPlant implements Comparable<KpiPlant> {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@Column(name = "TOOLTIP_MSG")
	private String tooltipMsg;
	
	@Column(name = "TIPO_KPI")
	private String tipoKpi;
	
	@Column(name = "FREQ_REGISTRAZIONE")
	private String freqRegistrazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;
	
	@Column(name = "ASSET")
	private String asset;
	
	public KpiPlant() {
		super();
	}
	
	public KpiPlant(String id, String descrizione, String tooltipMsg, String tipoKpi, String freqRegistrazione,
			Date insertionDate, String asset) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.tooltipMsg = tooltipMsg;
		this.tipoKpi = tipoKpi;
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

	public String getTipoKpi() {
		return tipoKpi;
	}

	public void setTipoKpi(String tipoKpi) {
		this.tipoKpi = tipoKpi;
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
		return "KpiNetwork [id=" + id + ", descrizione=" + descrizione + ", tooltipMsg=" + tooltipMsg + ", tipoKpi="
				+ tipoKpi + ", freqRegistrazione=" + freqRegistrazione + ", insertionDate=" + insertionDate + ", asset="
				+ asset + "]";
	}

	@Override
	public int compareTo(KpiPlant o) {
		return this.getInsertionDate().compareTo(o.getInsertionDate());
	}
}
