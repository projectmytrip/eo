package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EWP")
public class EWP {

	// Numero eWP
	@Id
	@Column(name = "ID")
	private String numeroEWP;

	@Column(name = "FASE")
	private String status;

	// Descrizione del lavoro
	@Column(name = "DESCRIZIONE")
	private String descrizioneLavoro;

	// Appaltatore
	@Column(name = "APPALTATORE")
	private String appaltatore;

	// Impresa (esecutrice)
	@Column(name = "IMPRESA_ESECUTRICE")
	private String impresaEsecutrice;

	// Preposto ai lavori
	@Column(name = "PREPOSTO")
	private String prepostoAiLavori;

	// Richiedente
	@Column(name = "RICHIEDENTE")
	private String richiedente;

	// Unità richiedente
	@Column(name = "UNITA_RICHIEDENTE")
	private String unitaRichiedente;

	// Sorvegliante
	@Column(name = "SORVEGLIANTE")
	private String sorvegliante;

	// Autorizzante - analisi di Rischio
	@Column(name = "AUTORIZZANTE")
	private String autorizzanteAnalisiDiRischio;

	// Unità autorizzante
	@Column(name = "UNITA_AUTORIZZANTE")
	private String unitaAutorizzante;

	// Data chiusura - data fine lavori approvata
	@Column(name = "DATA_CHIUSURA")
	private Date dataChiusura;

	// Data - ora inizio autorizzazione
	@Column(name = "DATA_INIZIO_AUTORIZZAZIONE")
	private Date dataOraInizioAutorizzazione;

	// Data – ora scadenza autorizzazione
	@Column(name = "DATA_SCADENZA_AUTORIZZAZIONE")
	private Date dataOraScadenzaAutorizzazione;

	// Indicazione tipologia attività
	@Column(name = "TIPOLOGIA_ATTIVITA")
	private String indicazioneTipologiaAttivita;

	// Indicazione SCE (Y/N
	@Column(name = "SCE")
	private String indicazioneSCE;

	// Indicazione SCE (Y/N
	@Column(name = "DESCRIZIONE_SCE")
	private String descrizioneSCE;

	// sede tecnica
	@Column(name = "SEDE_TECNICA")
	private String sedeTecnica;

	// Data apertura (data inizio lavori approvata
	@Column(name = "DATA_APERTURA")
	private Date dataApertura;

	// Data scadenza (data fine proposta)
	@Column(name = "DATA_SCADENZA")
	private Date dataScadenza;

	// Sotto Area
	@Column(name = "AREA")
	private String sottoArea;

	@Column(name = "SORT")
	private String tags;

	@Column(name = "INSERTION_DATE")
	private LocalDateTime insertionDate;

	@Column(name = "ASSET")
	private String asset;

	public EWP() {
		// Auto-generated constructor stub
	}

	public String getNumeroEWP() {
		return numeroEWP;
	}

	public void setNumeroEWP(String numeroEWP) {
		this.numeroEWP = numeroEWP;
	}

	public String getDescrizioneLavoro() {
		return descrizioneLavoro;
	}

	public void setDescrizioneLavoro(String descrizioneLavoro) {
		this.descrizioneLavoro = descrizioneLavoro;
	}

	public String getAppaltatore() {
		return appaltatore;
	}

	public void setAppaltatore(String appaltatore) {
		this.appaltatore = appaltatore;
	}

	public String getImpresaEsecutrice() {
		return impresaEsecutrice;
	}

	public void setImpresaEsecutrice(String impresaEsecutrice) {
		this.impresaEsecutrice = impresaEsecutrice;
	}

	public String getPrepostoAiLavori() {
		return prepostoAiLavori;
	}

	public void setPrepostoAiLavori(String prepostoAiLavori) {
		this.prepostoAiLavori = prepostoAiLavori;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public String getUnitaRichiedente() {
		return unitaRichiedente;
	}

	public void setUnitaRichiedente(String unitaRichiedente) {
		this.unitaRichiedente = unitaRichiedente;
	}

	public String getSorvegliante() {
		return sorvegliante;
	}

	public void setSorvegliante(String sorvegliante) {
		this.sorvegliante = sorvegliante;
	}

	public String getAutorizzanteAnalisiDiRischio() {
		return autorizzanteAnalisiDiRischio;
	}

	public void setAutorizzanteAnalisiDiRischio(String autorizzanteAnalisiDiRischio) {
		this.autorizzanteAnalisiDiRischio = autorizzanteAnalisiDiRischio;
	}

	public String getUnitaAutorizzante() {
		return unitaAutorizzante;
	}

	public void setUnitaAutorizzante(String unitaAutorizzante) {
		this.unitaAutorizzante = unitaAutorizzante;
	}

	public Date getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public Date getDataOraInizioAutorizzazione() {
		return dataOraInizioAutorizzazione;
	}

	public void setDataOraInizioAutorizzazione(Date dataOraInizioAutorizzazione) {
		this.dataOraInizioAutorizzazione = dataOraInizioAutorizzazione;
	}

	public Date getDataOraScadenzaAutorizzazione() {
		return dataOraScadenzaAutorizzazione;
	}

	public void setDataOraScadenzaAutorizzazione(Date dataOraScadenzaAutorizzazione) {
		this.dataOraScadenzaAutorizzazione = dataOraScadenzaAutorizzazione;
	}

	public String getIndicazioneTipologiaAttivita() {
		return indicazioneTipologiaAttivita;
	}

	public void setIndicazioneTipologiaAttivita(String indicazioneTipologiaAttivita) {
		this.indicazioneTipologiaAttivita = indicazioneTipologiaAttivita;
	}

	public String getIndicazioneSCE() {
		return indicazioneSCE;
	}

	public void setIndicazioneSCE(String indicazioneSCE) {
		this.indicazioneSCE = indicazioneSCE;
	}

	public Date getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getSottoArea() {
		return sottoArea;
	}

	public void setSottoArea(String sottoArea) {
		this.sottoArea = sottoArea;
	}

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getDescrizioneSCE() {
		return descrizioneSCE;
	}

	public void setDescrizioneSCE(String descrizioneSCE) {
		this.descrizioneSCE = descrizioneSCE;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSedeTecnica() {
		return sedeTecnica;
	}

	public void setSedeTecnica(String sedeTecnica) {
		this.sedeTecnica = sedeTecnica;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
