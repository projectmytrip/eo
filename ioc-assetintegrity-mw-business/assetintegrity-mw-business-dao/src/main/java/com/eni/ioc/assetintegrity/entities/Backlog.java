package com.eni.ioc.assetintegrity.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BACKLOG")
public class Backlog implements Comparable<Backlog> {

	@Column(name = "N_ODM")
	private String nOdm;

	@Id
	@Column(name = "ODM")
	private String odm;

	@Column(name = "TIPO_MANUTENZIONE")
	private String tipoManutenzione;
	// @Id
	@Column(name = "COMPONENTE")
	private String componente;

	@Column(name = "COMPONENTE_INFO")
	private String componenteInfo;

	@Column(name = "TIPO_ELEMENTO")
	private String tipoElemento;

	@Column(name = "DATA_CHIUSURA_PREV")
	private String dataChiusuraPrev;

	@Column(name = "UNITA_FUNZIONALE")
	private String unitaFunzionale;

	@Column(name = "TAB")
	private String tab;

	@Column(name = "AREA")
	private String area;

	@Column(name = "DURATA")
	private String durata;

	@Column(name = "UNITA")
	private String unita;

	@Column(name = "STATO_UTENTE_ODM")
	private String statoUtenteOdm;

	@Column(name = "DATA_INIZIO_CARDINE")
	private String dataInizioCardine;

	private String color; // campo non presente sulla tabella

	private String italianColor; // campo non presente sulla tabella

	private String dataCambioStato; // campo non presente sulla tabella

	public Backlog() {
		super();
	}

	public String getnOdm() {
		return nOdm;
	}

	public void setnOdm(String nOdm) {
		this.nOdm = nOdm;
	}

	public String getOdm() {
		return odm;
	}

	public void setOdm(String odm) {
		this.odm = odm;
	}

	public String getTipoManutenzione() {
		return tipoManutenzione;
	}

	public void setTipoManutenzione(String tipoManutenzione) {
		this.tipoManutenzione = tipoManutenzione;
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

	public String getComponenteInfo() {
		return componenteInfo;
	}

	public void setComponenteInfo(String componenteInfo) {
		this.componenteInfo = componenteInfo;
	}

	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public String getDataChiusuraPrev() {
		return dataChiusuraPrev;
	}

	public void setDataChiusuraPrev(String dataChiusuraPrev) {
		this.dataChiusuraPrev = dataChiusuraPrev;
	}

	public String getUnitaFunzionale() {
		return unitaFunzionale;
	}

	public void setUnitaFunzionale(String unitaFunzionale) {
		this.unitaFunzionale = unitaFunzionale;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDurata() {
		return durata;
	}

	public void setDurata(String durata) {
		this.durata = durata;
	}

	public String getUnita() {
		return unita;
	}

	public void setUnita(String unita) {
		this.unita = unita;
	}

	public String getStatoUtenteOdm() {
		return statoUtenteOdm;
	}

	public void setStatoUtenteOdm(String statoUtenteOdm) {
		this.statoUtenteOdm = statoUtenteOdm;
	}

	public String getDataInizioCardine() {
		return dataInizioCardine;
	}

	public void setDataInizioCardine(String dataInizioCardine) {
		this.dataInizioCardine = dataInizioCardine;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getItalianColor() {
		return italianColor;
	}

	public void setItalianColor(String italianColor) {
		this.italianColor = italianColor;
	}

	public String getDataCambioStato() {
		return dataCambioStato;
	}

	public void setDataCambioStato(String dataCambioStato) {
		this.dataCambioStato = dataCambioStato;
	}

	@Override
	public int compareTo(Backlog o) {
		return this.getOdm().compareTo(o.getOdm());
	}
}
