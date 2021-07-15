package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

public class WellKpiDataDto implements Serializable {
	
    private static final long serialVersionUID = 2961724763822376551L;

    private String id;
    private String n;
    private String perc;
    private String ultimoUpdate;
    private String insertionDate;
    
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
	public String getInsertionDate() {
		return insertionDate;
	}
	public void setInsertionDate(String insertionDate) {
		this.insertionDate = insertionDate;
	}

    
    
}
