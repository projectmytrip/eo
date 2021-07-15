package com.eni.ioc.dailyworkplan.api;

import java.io.Serializable;
import java.util.Date;

public class ReportFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324359854322778126L;
	private Date fromDate;
	private Date toDate;
	private String societa;
	private String attivatore;
	private String odm;
	private String mezzo;
	private String item;
	private String supervisore;
	private String area;
	private String squadra;
	private String assistenza;
	private String operatoreAvanzamento;
	private String avanzamento;
	private String orario;
	private String priorita;
	
	
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}
	public String getAttivatore() {
		return attivatore;
	}
	public void setAttivatore(String attivatore) {
		this.attivatore = attivatore;
	}
	public String getOdm() {
		return odm;
	}
	public void setOdm(String odm) {
		this.odm = odm;
	}
	public String getMezzo() {
		return mezzo;
	}
	public void setMezzo(String mezzo) {
		this.mezzo = mezzo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getSupervisore() {
		return supervisore;
	}
	public void setSupervisore(String supervisore) {
		this.supervisore = supervisore;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSquadra() {
		return squadra;
	}
	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}
	public String getAssistenza() {
		return assistenza;
	}
	public void setAssistenza(String assistenza) {
		this.assistenza = assistenza;
	}
	public String getOperatoreAvanzamento() {
		return operatoreAvanzamento;
	}
	public void setOperatoreAvanzamento(String operatoreAvanzamento) {
		this.operatoreAvanzamento = operatoreAvanzamento;
	}
	public String getAvanzamento() {
		return avanzamento;
	}
	public void setAvanzamento(String avanzamento) {
		this.avanzamento = avanzamento;
	}
	public String getOrario() {
		return orario;
	}
	public void setOrario(String orario) {
		this.orario = orario;
	}
	public String getPriorita() {
		return priorita;
	}
	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
	
}
