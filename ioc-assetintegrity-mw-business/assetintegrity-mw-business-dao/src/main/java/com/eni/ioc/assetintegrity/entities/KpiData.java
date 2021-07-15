package com.eni.ioc.assetintegrity.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class KpiData implements Comparable<KpiData> {
    	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "VALORE")
	private String valore;
	
	@Column(name = "STATO")
	private String stato;
	
	@Column(name = "ULTIMO_UPDATE")
	private String ultimoUpdate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;
        
        
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
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
		return "KpiNetworkData [id=" + id + ", valore=" + valore + ", stato=" + stato + ", ultimoUpdate=" + ultimoUpdate
				+ ", insertionDate=" + insertionDate + "]";
	}

	@Override
	public int compareTo(KpiData o) {
		return this.getInsertionDate().compareTo(o.getInsertionDate());
	}
}
