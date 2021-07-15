package com.eni.ioc.assetintegrity.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EWPDto {

	// Status
	@JsonProperty("fase")
	private String status;

	// Numero eWP
	@JsonProperty("nEwp")
	private String numeroEWP;

	// Descrizione del lavoro
	@JsonProperty("descrizione")
	private String descrizioneLavoro;

	// Appaltatore
	@JsonProperty("appaltatore")
	private String appaltatore;

	// Impresa (esecutrice)
	@JsonProperty("impresaEsecutrice")
	private String impresaEsecutrice;

	// Preposto ai lavori
	@JsonProperty("preposto")
	private String prepostoAiLavori;

	// Richiedente
	@JsonProperty("richiedente")
	private String richiedente;

	// Unità richiedente
	@JsonProperty("unitaRichiedente")
	private String unitRichiedente;

	// Sorvegliante
	@JsonProperty("sorvegliante")
	private String sorvegliante;

	// Autorizzante - analisi di Rischio
	@JsonProperty("autorizzante")
	private String autorizzanteAnalisiDiRischio;

	// Unità autorizzante
	@JsonProperty("unitaAutorizzante")
	private String unitaAutorizzante;

	// Data chiusura - data fine lavori approvata
	@JsonProperty("dataOraChiusura")
	private Date dataChiusura;

	// Data - ora inizio autorizzazione
	@JsonProperty("dataOraAutorizzazione")
	private Date dataOraInizioAutorizzazione;

	// Data – ora scadenza autorizzazione
	@JsonProperty("dataOraScadenzaAutorizzazione")
	private Date dataOraScadenzaAutorizzazione;

	// Indicazione tipologia attività
	@JsonProperty("tipologiaAttivita")
	private List<String> indicazioneTipologiaAttivita;

	// Indicazione SCE (Y/N
	@JsonProperty("siglaSCE")
	private String indicazioneSCE;

	// descrizioneSCE
	@JsonProperty("descrizioneSCE")
	private String descrizioneSCE;

	// Data apertura (data inizio lavori approvata
	@JsonProperty("dataOraApertura")
	private Date dataApertura;

	// Data scadenza (data fine proposta)
	@JsonProperty("dataScadenza")
	private Date dataScadenza;

	// sedeTecnica
	@JsonProperty("sedeTecnica")
	private String sedeTecnica;

	// Sotto Area
	@JsonProperty("area")
	private List<String> sottoArea;

	@JsonProperty("sort")
	private List<String> sort;

	public EWPDto() {
		// Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUnitRichiedente() {
		return unitRichiedente;
	}

	public void setUnitRichiedente(String unitRichiedente) {
		this.unitRichiedente = unitRichiedente;
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

	public List<String> getIndicazioneTipologiaAttivita() {
		return indicazioneTipologiaAttivita;
	}

	public void setIndicazioneTipologiaAttivita(List<String> indicazioneTipologiaAttivita) {
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

	public List<String> getSottoArea() {
		return sottoArea;
	}

	public void setSottoArea(List<String> sottoArea) {
		this.sottoArea = sottoArea;
	}

	public String getDescrizioneSCE() {
		return descrizioneSCE;
	}

	public void setDescrizioneSCE(String descrizioneSCE) {
		this.descrizioneSCE = descrizioneSCE;
	}

	public String getSedeTecnica() {
		return sedeTecnica;
	}

	public void setSedeTecnica(String sedeTecnica) {
		this.sedeTecnica = sedeTecnica;
	}

	public List<String> getSort() {
		return sort;
	}

	public void setSort(List<String> sort) {
		this.sort = sort;
	}

}
