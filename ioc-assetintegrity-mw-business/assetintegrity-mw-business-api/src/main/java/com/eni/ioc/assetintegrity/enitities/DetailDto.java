package com.eni.ioc.assetintegrity.enitities;

import java.io.Serializable;
import java.util.Date;

public class DetailDto implements Serializable {

	private static final long serialVersionUID = 4063348518652917231L;

	String id;
	String descrizione;
	String tooltip;
	String valore;
	String perc;
	String tipoKpi;
	Integer status;
	String freqReg;
	String lastUpdate;
	String asset;
	Date insertionDate;

	public DetailDto() {
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

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getPerc() {
		return perc;
	}

	public void setPerc(String perc) {
		this.perc = perc;
	}

	public String getTipoKpi() {
		return tipoKpi;
	}

	public void setTipoKpi(String tipoKpi) {
		this.tipoKpi = tipoKpi;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFreqReg() {
		return freqReg;
	}

	public void setFreqReg(String freqReg) {
		this.freqReg = freqReg;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public String getAsset() {
		return asset;
	}
	
	public void setAsset(String asset) {
		this.asset = asset;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	@Override
	public String toString() {
		return "DetailDto [id=" + id + ", descrizione=" + descrizione + ", tooltip=" + tooltip + ", valore=" + valore
				+ ", perc=" + perc + ", tipoKpi=" + tipoKpi + ", status=" + status + ", freqReg=" + freqReg
				+ ", lastUpdate=" + lastUpdate + ", asset="+ asset +"]";
	}
}
