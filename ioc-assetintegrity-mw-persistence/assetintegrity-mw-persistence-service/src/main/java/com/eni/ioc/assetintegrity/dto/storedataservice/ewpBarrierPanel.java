package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ewpBarrierPanel {

	public enum tipoMessaggio {APERTURA, RINNOVO, SOSPENSIONE, SCADENZA, CHIUSURA};
	
	// Status
	@JsonProperty("tipoMessaggio")
	private tipoMessaggio status;

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
	private String dataChiusura;

	// Data - ora inizio autorizzazione
	@JsonProperty("dataOraAutorizzazione")
	private String dataOraInizioAutorizzazione;

	// Data – ora scadenza autorizzazione
	@JsonProperty("dataOraScadenzaAutorizzazione")
	private String dataOraScadenzaAutorizzazione;

	// Indicazione tipologia attività
	@JsonProperty("tipologiaAttivita")
	private String indicazioneTipologiaAttivita;

	// Indicazione SCE (Y/N
	@JsonProperty("siglaSCE")
	private String indicazioneSCE;

	// descrizioneSCE
	@JsonProperty("descrizioneSCE")
	private String descrizioneSCE;

	// Data apertura (data inizio lavori approvata
	@JsonProperty("dataOraApertura")
	private String dataApertura;

	// Data scadenza (data fine proposta)
	@JsonProperty("dataScadenza")
	private String dataScadenza;

	// sedeTecnica
	@JsonProperty("sedeTecnica")
	private String sedeTecnica;

	// Sotto Area
	@JsonProperty("area")
	private String sottoArea;
	
	// sort
	@JsonProperty("sort")
	private String sort;

	public ewpBarrierPanel() {
		// Auto-generated constructor stub
	}

	public tipoMessaggio getStatus() {
		return status;
	}

	public void setStatus(tipoMessaggio status) {
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

	public String getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getDataOraInizioAutorizzazione() {
		return dataOraInizioAutorizzazione;
	}

	public void setDataOraInizioAutorizzazione(String dataOraInizioAutorizzazione) {
		this.dataOraInizioAutorizzazione = dataOraInizioAutorizzazione;
	}

	public String getDataOraScadenzaAutorizzazione() {
		return dataOraScadenzaAutorizzazione;
	}

	public void setDataOraScadenzaAutorizzazione(String dataOraScadenzaAutorizzazione) {
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

	public String getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getSottoArea() {
		return sottoArea;
	}

	public void setSottoArea(String sottoArea) {
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

	public String getSort() {
		return sort;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
}
