package com.eni.ioc.assetintegrity.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "KPI_WELLDATA")
public class KpiWellData implements Comparable<KpiWellData> {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "N")
	private String n;
	
	@Column(name = "PERC")
	private String perc;

	@Column(name = "ULTIMO_UPDATE")
	private String ultimoUpdate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;

	public KpiWellData() {
		super();
	}
	
	public KpiWellData(String id, String n, String perc, String ultimoUpdate, Date insertionDate) {
		super();
		this.id = id;
		this.n = n;
		this.perc = perc;
		this.ultimoUpdate = ultimoUpdate;
		this.insertionDate = insertionDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getPerc() {
		return perc;
	}

	public void setPerc(String perc) {
		this.perc = perc;
	}

	public String getUltimoUpdate() {
		return ultimoUpdate;
	}

	public void setUltimoUpdate(String ultimoUpdate) {
		this.ultimoUpdate = ultimoUpdate;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}
	
	@Override
	public String toString() {
		return "KpiNetworkData [id=" + id + ", n=" + n + ", perc=" + perc + ", ultimoUpdate=" + ultimoUpdate
				+ ", insertionDate=" + insertionDate + "]";
	}

	@Override
	public int compareTo(KpiWellData o) {
		return this.getInsertionDate().compareTo(o.getInsertionDate());
	}
}
