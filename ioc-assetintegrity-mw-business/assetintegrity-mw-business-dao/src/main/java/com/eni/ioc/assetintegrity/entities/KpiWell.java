package com.eni.ioc.assetintegrity.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "KPI_WELL")
public class KpiWell implements Comparable<KpiWell> {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "WELL_ALARM_STATUS")
	private String wellAlarmStatus;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "FREQ_REGISTRAZIONE")
	private String freqRegistrazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;
	
	@Column(name = "ASSET")
	private String asset;
	
	public KpiWell() {
		super();
	}

	public KpiWell(String id, String wellAlarmStatus, Integer status, String freqRegistrazione, Date insertionDate,
			String asset) {
		super();
		this.id = id;
		this.wellAlarmStatus = wellAlarmStatus;
		this.status = status;
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

	public String getWellAlarmStatus() {
		return wellAlarmStatus;
	}

	public void setWellAlarmStatus(String wellAlarmStatus) {
		this.wellAlarmStatus = wellAlarmStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "KpiWell [id=" + id + ", wellAlarmStatus=" + wellAlarmStatus + ", status=" + status + ", freqRegistrazione="
				+ freqRegistrazione + ", insertionDate=" + insertionDate + ", asset=" + asset + "]";
	}

	@Override
	public int compareTo(KpiWell o) {
		return this.getInsertionDate().compareTo(o.getInsertionDate());
	}
}
