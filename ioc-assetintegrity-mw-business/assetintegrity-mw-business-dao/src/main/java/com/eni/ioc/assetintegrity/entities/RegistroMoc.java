package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REGISTRO_MOC")
public class RegistroMoc implements Serializable {
	
	private static final long serialVersionUID = 0L;
	
	// key: string to show in spreadsheet cell
	// value: method to get value
	public static Map<String, String> excelOrder = new LinkedHashMap<>();
	
	// key: index
	// value: a map, key is label, value is map with starting and ending column
	public static Map<Integer, Map<String, Map<Integer, Integer>>> excelHeader = new LinkedHashMap<>();
	
	static {
		Map<String, Map<Integer, Integer>> firstRow = new LinkedHashMap<>();
		Map<String, Map<Integer, Integer>> secondRow = new LinkedHashMap<>();
		
		// -- first row --
		
		Map<Integer, Integer> mapCoord1 = new LinkedHashMap<>();
		mapCoord1.put(0,36);
		firstRow.put("Registro", mapCoord1);	
		
		Map<Integer, Integer> mapCoord2 = new LinkedHashMap<>();
		mapCoord2.put(37,75);
		firstRow.put("Parte B3", mapCoord2);
		
		excelHeader.put(0, firstRow);
		
		// -- second row --
		
		Map<Integer, Integer> mapCoord3 = new LinkedHashMap<>();
		mapCoord3.put(0,9);
		secondRow.put("Parte A", mapCoord3);
		
		Map<Integer, Integer> mapCoord4 = new LinkedHashMap<>();
		mapCoord4.put(10,22);
		secondRow.put("Parte B4", mapCoord4);
		
		Map<Integer, Integer> mapCoord5 = new LinkedHashMap<>();
		mapCoord5.put(23,26);
		secondRow.put("Parte C", mapCoord5);
		
		Map<Integer, Integer> mapCoord6 = new LinkedHashMap<>();
		mapCoord6.put(27,27);
		secondRow.put("Parte D", mapCoord6);
		
		Map<Integer, Integer> mapCoord7 = new LinkedHashMap<>();
		mapCoord7.put(28,30);
		secondRow.put("Parte E", mapCoord7);
		
		Map<Integer, Integer> mapCoord8 = new LinkedHashMap<>();
		mapCoord8.put(31,33);
		secondRow.put("Parte F", mapCoord8);
		
		Map<Integer, Integer> mapCoord9 = new LinkedHashMap<>();
		mapCoord9.put(34,36);
		secondRow.put("Parte G", mapCoord9);
		
		Map<Integer, Integer> mapCoord10 = new LinkedHashMap<>();
		mapCoord10.put(37,46);
		secondRow.put("Manutenzione e affidabilità / Ispezioni", mapCoord10);
		
		Map<Integer, Integer> mapCoord11 = new LinkedHashMap<>();
		mapCoord11.put(47,57);
		secondRow.put("Salute, Sicurezza e Ambiente", mapCoord11);
		
		Map<Integer, Integer> mapCoord12 = new LinkedHashMap<>();
		mapCoord12.put(58,62);
		secondRow.put("Addestramenti", mapCoord12);
		
		Map<Integer, Integer> mapCoord13 = new LinkedHashMap<>();
		mapCoord13.put(63,72);
		secondRow.put("Aggiornamento documentazione di riferim.", mapCoord13);
		
		Map<Integer, Integer> mapCoord14 = new LinkedHashMap<>();
		mapCoord14.put(73,75);
		secondRow.put("Varie", mapCoord14);
		
		excelHeader.put(1, secondRow);
		
		excelOrder.put("NUMERO", "getNumero");
		excelOrder.put("ANNO", "getAnno");
		excelOrder.put("TITOLO", "getTitolo");
		excelOrder.put("RICHIEDENTE", "getRichiedente");
		excelOrder.put("SEGUITA DA", "getSeguitaDa");
		excelOrder.put("DATA REGISTRAZIONE", "getDataRegistrazione");
		excelOrder.put("TIPO MODIFICA", "getTipoModificaA");
		excelOrder.put("TEMPORANEA", "getTemporanea");
		excelOrder.put("PRIORITA MODIFICA", "getPrioritaModifica");
		excelOrder.put("MOTIVO", "getMotivo");
		excelOrder.put("DATA RIUNIONE", "getDataRiunione");
		excelOrder.put("MODA", "getModA");
		excelOrder.put("MODB2", "getModB2");
		excelOrder.put("MODB3", "getModB3");
		excelOrder.put("MODB4", "getModB4");
		excelOrder.put("ESITO RIUNIONE", "getEsitoRiunione");
		excelOrder.put("STATUS APPROFONDIMENTI", "getStatusApprofondimenti");
		excelOrder.put("CLASSIFICAZIONE MODIFICA PER ITER AUTORIZZATIVO", "getClassificazioneModificaPerIterAutorizzativo");
		excelOrder.put("TIPO MODIFICA B4", "getTipoModificaB4");
		excelOrder.put("NOTE", "getNote");
		excelOrder.put("STATO MODIFICHE", "getStatoModifiche");
		excelOrder.put("DATA FIRMA MODULO B4", "getDataFirmaModuloB4");
		excelOrder.put("IN FASE DI REALIZZAZIONE", "getInFaseDiRealizzazione");
		excelOrder.put("DATA APPROVAZIONE", "getDataApprovazione");
		excelOrder.put("APPROVAZIONE", "getApprovazione");
		excelOrder.put("DATA AUTORIZZAZIONE", "getDataAutorizzazione");
		excelOrder.put("AUTORIZZAZIONE", "getAutorizzazione");
		excelOrder.put("DATA FINE PROGETTAZIONE", "getDataFineProgettazione");
		excelOrder.put("DATA FINE REALIZZAZIONE", "getDataFineRealizzazione");
		excelOrder.put("REALIZZAZIONE", "getRealizzazione");
		excelOrder.put("OTTENUTE AUTORIZZAZIONI PREVISTE", "getOttenuteAutorizzazioniPreviste");
		excelOrder.put("RICEVENTE", "getRicevente");
		excelOrder.put("CONSEGNA", "getConsegna");
		excelOrder.put("DATA ASSEGNAZIONE", "getDataAssegnazione");
		excelOrder.put("DATA RIPRISTINO", "getDataRipristino");
		excelOrder.put("DATA CHIUSURA", "getDataChiusura");
		excelOrder.put("CHIUSURA", "getChiusura");
		excelOrder.put("PROCEDURE MANUTENZIONE", "getProcedureManutenzione");
		excelOrder.put("ELEMENTI CRITICI", "getElementiCritici");
		excelOrder.put("LISTA STRUMENTI CRITICI", "getListaStrumentiCritici");
		excelOrder.put("ATTIVITA PED", "getAttivitaPED");
		excelOrder.put("ATTIVITA ATEX", "getAttivitaATEX");
		excelOrder.put("PRATICHE", "getPratiche");
		excelOrder.put("VERIFICA PSV", "getVerificaPSV");
		excelOrder.put("AGGIORNAMENTO PIANO ISPEZIONI", "getAggiornamentoPianoIspezioni");
		excelOrder.put("INFO MANUTENZIONE", "getInfoManutenzione");
		excelOrder.put("ALTRO MAI", "getAltroMai");
		excelOrder.put("AUTORIZZAZIONI AMBIENTALI", "getAutorizzazioniAmbientali");
		excelOrder.put("IDONEA ATTREZZATURA ANTINCENDIO", "getIsIdoneaAttrezzaturaAntincendio");
		excelOrder.put("DSSC", "getDssc");
		excelOrder.put("ANALISI OPERATIVA RISCHIO", "getAnalisiOperativaRischio");
		excelOrder.put("ANALISI ASPETTI AMBIENTALI", "getAnalisiAspettiAmbientali");
		excelOrder.put("REVISIONE RISCHI PROCESSO", "getRevisioneRischiProcesso");
		excelOrder.put("PROCEDURE SGI", "getProcedureSGI");
		excelOrder.put("REVISIONE PEI", "getRevisionePEI");
		excelOrder.put("ALTRO SSA", "getAltroSsa");
		excelOrder.put("AGGIORNAMENTO MANUALI OPERATIVI", "getAggiornamentoManualiOperativi");
		excelOrder.put("FORMAZIONE", "getFormazione");
		excelOrder.put("ADDESTRAMENTO OPERATORI", "getAddestramentoOperatori");
		excelOrder.put("AGGIORNAMENTO INFORMATICO SCHEDE SICUREZZA", "getAggiornamentoInformaticoSchedeSicurezza");
		excelOrder.put("ALTRO ADD", "getAltroAdd");
		excelOrder.put("CLASSIFICAZIONE AREE", "getClassificazioneAree");
		excelOrder.put("PROCESS FLOW DIAGRAM", "getProcessFlowDiagram");
		excelOrder.put("PID", "getPID");
		excelOrder.put("SCHEMI ELETTROSTRUMENTALI", "getSchemiElettrostrumentali");
		excelOrder.put("SCHEMI ELETTRICI", "getSchemiElettrici");
		excelOrder.put("REGISTRO ALLARMI", "getRegistroAllarmi");
		excelOrder.put("REGISTRO BLOCCHI", "getRegistroBlocchi");
		excelOrder.put("SISTEMI CONTROLLO", "getSistemiControllo");
		excelOrder.put("AGGIORNAMENTO PLC", "getAggiornamentoPLC");
		excelOrder.put("AGGIORNAMENTO DCS", "getAggiornamentoDCS");
		excelOrder.put("ALTRO ADR", "getAltroAdr");
		excelOrder.put("PRATICHE FISCALI", "getPraticheFiscali");
		excelOrder.put("UNMIG", "getUnmig");
		excelOrder.put("ALTRO VARIE", "getAltroVarie");
		
	}
	
    @Id
    @Column(name="NUMERO")
    private String numero;
	@Column(name="WF_ID")
    private String wfId; //creare link per reindirizzamento a step corrente del wf
    @Column(name="WF_ID_RIPRISTINO")
    private String wfIdRipristino;
    @Column(name="ANNO")
	private String anno;
    @Column(name="TITOLO")
	private String titolo;
	/* Parte A */
    @Column(name="RICHIEDENTE")
	private String richiedente;
    @Column(name="SEGUITADA")
	private String seguitaDa;
    @Column(name="DATAREGISTRAZIONE")
	private Date dataRegistrazione;
    @Column(name="TIPOMODIFICAA")
	private String tipoModificaA;
    @Column(name="TEMPORANEA")
	private String temporanea; //Boolean
    @Column(name="PRIORITAMODIFICA")
    private String prioritaModifica;
    @Column(name="MOTIVO")
    private String motivo;
	/* Parte B4 */
    @Column(name="DATARIUNIONE")
	private Date dataRiunione;
    @Column(name="MODA")
    private String modA;
    @Column(name="MODB2")
    private String modB2;
    @Column(name="MODB3")
    private String modB3;
    @Column(name="MODB4")
    private String modB4;
	@Column(name="ESITORIUNIONE")
	private String esitoRiunione;
	@Column(name="STATUSAPPROFONDIMENTI")
	private String statusApprofondimenti;
	@Column(name="CLASSIFICAZIONEMODIFICAPERITERAUTORIZZATIVO")
	private String classificazioneModificaPerIterAutorizzativo;
	@Column(name="TIPOMODIFICAB4")
	private String tipoModificaB4;
	@Column(name="NOTE")
	private String note;
	@Column(name="STATOMODIFICHE")
	private String statoModifiche;
	@Column(name="DATAFIRMAMODULOB4")
	private Date dataFirmaModuloB4;
	@Column(name="INFASEDIREALIZZAZIONE")
	private String inFaseDiRealizzazione; //Boolean
	/* Parte C*/
	@Column(name="DATAAPPROVAZIONE")
	private Date dataApprovazione;
	@Column(name="APPROVAZIONE")
	private String approvazione;
	@Column(name="DATAAUTORIZZAZIONE")
	private Date dataAutorizzazione;
	@Column(name="AUTORIZZAZIONE")
	private String autorizzazione;
	/* Parte D */
	@Column(name="DATAFINEPROGETTAZIONE")
	private Date dataFineProgettazione;
	/* Parte E */
	@Column(name="DATAFINEREALIZZAZIONE")
	private Date dataFineRealizzazione;
	@Column(name="REALIZZAZIONE")
	private String realizzazione;
	@Column(name="OTTENUTEAUTORIZZAZIONIPREVISTE")
	private String ottenuteAutorizzazioniPreviste; //Boolean
	/* Parte F */
	@Column(name="RICEVENTE")
	private String ricevente;
	@Column(name="CONSEGNA")
	private String consegna; //Boolean
	@Column(name="DATAASSEGNAZIONE")
	private Date dataAssegnazione;
	/* Parte G */
	@Column(name="DATARIPRISTINO")
	private Date dataRipristino;
	@Column(name="DATACHIUSURA")
	private Date dataChiusura;
	@Column(name="CHIUSURA")
	private String chiusura; //Boolean
	/* Parte B3: Manutenzione e affidabilità / Ispezioni */
	@Column(name="PROCEDUREMANUTENZIONE")
	private String procedureManutenzione;
	@Column(name="ELEMENTICRITICI")
	private String elementiCritici;
	@Column(name="LISTASTRUMENTICRITICI")
	private String listaStrumentiCritici;
	@Column(name="ATTIVITAPED")
	private String attivitaPED;
	@Column(name="ATTIVITAATEX")
	private String attivitaATEX;
	@Column(name="PRATICHE")
	private String pratiche;
	@Column(name="VERIFICAPSV")
	private String verificaPSV;
	@Column(name="AGGIORNAMENTOPIANOISPEZIONI")
	private String aggiornamentoPianoIspezioni;
	@Column(name="INFOMANUTENZIONE")
	private String infoManutenzione;
	@Column(name="ALTROMAI")
	private String altroMai;
	/* Parte B3: Salute, Sicurezza e Ambiente  */
	@Column(name="AUTORIZZAZIONIAMBIENTALI")
	private String autorizzazioniAmbientali;
	@Column(name="ISIDONEAATTREZZATURAANTINCENDIO")
	private String isIdoneaAttrezzaturaAntincendio; //Boolean
	@Column(name="DSSC")
	private String dssc;
	@Column(name="ANALISIOPERATIVARISCHIO")
	private String analisiOperativaRischio;
	@Column(name="ANALISIASPETTIAMBIENTALI")
	private String analisiAspettiAmbientali;
	@Column(name="REVISIONERISCHIPROCESSO")
	private String revisioneRischiProcesso;
	@Column(name="PROCEDURESGI")
	private String procedureSGI;
	@Column(name="REVISIONEPEI")
	private String revisionePEI;
	@Column(name="ALTROSSA")
	private String altroSsa;
	/* Parte B3: Addestramenti */
	@Column(name="AGGIORNAMENTOMANUALIOPERATIVI")
	private String aggiornamentoManualiOperativi;
	@Column(name="FORMAZIONE")
	private String formazione;
	@Column(name="ADDESTRAMENTOOPERATORI")
	private String addestramentoOperatori;
	@Column(name="AGGIORNAMENTOINFORMATICOSCHEDESICUREZZA")
	private String aggiornamentoInformaticoSchedeSicurezza;
	@Column(name="ALTROADD")
	private String altroAdd;	
	/* Parte B3: Aggiornamento documentazione di riferim. */
	@Column(name="CLASSIFICAZIONEAREE")
	private String classificazioneAree;
	@Column(name="PROCESSFLOWDIAGRAM")
	private String processFlowDiagram;
	@Column(name="PID")
	private String PID;
	@Column(name="SCHEMIELETTROSTRUMENTALI")
	private String schemiElettrostrumentali;
	@Column(name="SCHEMIELETTRICI")
	private String schemiElettrici;
	@Column(name="REGISTROALLARMI")
	private String registroAllarmi;
	@Column(name="REGISTROBLOCCHI")
	private String registroBlocchi;
	@Column(name="SISTEMICONTROLLO")
	private String sistemiControllo;
	@Column(name="AGGIORNAMENTOPLC")
	private String aggiornamentoPLC;
	@Column(name="AGGIORNAMENTODCS")
	private String aggiornamentoDCS;
	@Column(name="ALTROADR")
	private String altroAdr;
	/* Parte B3: Varie */
	@Column(name="PRATICHEFISCALI")
	private String praticheFiscali;
	@Column(name="UNMIG")
	private String unmig;
	@Column(name="ALTROVARIE")
	private String altroVarie;
	
	public String getWfId() {
		return wfId;
	}
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	public String getWfIdRipristino() {
		return wfIdRipristino;
	}
	public void setWfIdRipristino(String wfIdRipristino) {
		this.wfIdRipristino = wfIdRipristino;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}
	public String getSeguitaDa() {
		return seguitaDa;
	}
	public void setSeguitaDa(String seguitaDa) {
		this.seguitaDa = seguitaDa;
	}
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getTipoModificaA() {
		return tipoModificaA;
	}
	public void setTipoModificaA(String tipoModificaA) {
		this.tipoModificaA = tipoModificaA;
	}
	public String getTemporanea() {
		return temporanea;
	}
	public void setTemporanea(String temporanea) {
		this.temporanea = temporanea;
	}
	public String getPrioritaModifica() {
		return prioritaModifica;
	}
	public void setPrioritaModifica(String prioritaModifica) {
		this.prioritaModifica = prioritaModifica;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Date getDataRiunione() {
		return dataRiunione;
	}
	public void setDataRiunione(Date dataRiunione) {
		this.dataRiunione = dataRiunione;
	}
	public String getModA() {
		return modA;
	}
	public void setModA(String modA) {
		this.modA = modA;
	}
	public String getModB2() {
		return modB2;
	}
	public void setModB2(String modB2) {
		this.modB2 = modB2;
	}
	public String getModB3() {
		return modB3;
	}
	public void setModB3(String modB3) {
		this.modB3 = modB3;
	}
	public String getModB4() {
		return modB4;
	}
	public void setModB4(String modB4) {
		this.modB4 = modB4;
	}
	public String getEsitoRiunione() {
		return esitoRiunione;
	}
	public void setEsitoRiunione(String esitoRiunione) {
		this.esitoRiunione = esitoRiunione;
	}
	public String getStatusApprofondimenti() {
		return statusApprofondimenti;
	}
	public void setStatusApprofondimenti(String statusApprofondimenti) {
		this.statusApprofondimenti = statusApprofondimenti;
	}
	public String getClassificazioneModificaPerIterAutorizzativo() {
		return classificazioneModificaPerIterAutorizzativo;
	}
	public void setClassificazioneModificaPerIterAutorizzativo(String classificazioneModificaPerIterAutorizzativo) {
		this.classificazioneModificaPerIterAutorizzativo = classificazioneModificaPerIterAutorizzativo;
	}
	public String getTipoModificaB4() {
		return tipoModificaB4;
	}
	public void setTipoModificaB4(String tipoModificaB4) {
		this.tipoModificaB4 = tipoModificaB4;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatoModifiche() {
		return statoModifiche;
	}
	public void setStatoModifiche(String statoModifiche) {
		this.statoModifiche = statoModifiche;
	}
	public Date getDataFirmaModuloB4() {
		return dataFirmaModuloB4;
	}
	public void setDataFirmaModuloB4(Date dataFirmaModuloB4) {
		this.dataFirmaModuloB4 = dataFirmaModuloB4;
	}
	public String getInFaseDiRealizzazione() {
		return inFaseDiRealizzazione;
	}
	public void setInFaseDiRealizzazione(String inFaseDiRealizzazione) {
		this.inFaseDiRealizzazione = inFaseDiRealizzazione;
	}
	public Date getDataApprovazione() {
		return dataApprovazione;
	}
	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}
	public String getApprovazione() {
		return approvazione;
	}
	public void setApprovazione(String approvazione) {
		this.approvazione = approvazione;
	}
	public Date getDataAutorizzazione() {
		return dataAutorizzazione;
	}
	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}
	public String getAutorizzazione() {
		return autorizzazione;
	}
	public void setAutorizzazione(String autorizzazione) {
		this.autorizzazione = autorizzazione;
	}
	public Date getDataFineProgettazione() {
		return dataFineProgettazione;
	}
	public void setDataFineProgettazione(Date dataFineProgettazione) {
		this.dataFineProgettazione = dataFineProgettazione;
	}
	public Date getDataFineRealizzazione() {
		return dataFineRealizzazione;
	}
	public void setDataFineRealizzazione(Date dataFineRealizzazione) {
		this.dataFineRealizzazione = dataFineRealizzazione;
	}
	public String getRealizzazione() {
		return realizzazione;
	}
	public void setRealizzazione(String realizzazione) {
		this.realizzazione = realizzazione;
	}
	public String getOttenuteAutorizzazioniPreviste() {
		return ottenuteAutorizzazioniPreviste;
	}
	public void setOttenuteAutorizzazioniPreviste(String ottenuteAutorizzazioniPreviste) {
		this.ottenuteAutorizzazioniPreviste = ottenuteAutorizzazioniPreviste;
	}
	public String getRicevente() {
		return ricevente;
	}
	public void setRicevente(String ricevente) {
		this.ricevente = ricevente;
	}
	public String getConsegna() {
		return consegna;
	}
	public void setConsegna(String consegna) {
		this.consegna = consegna;
	}
	public Date getDataAssegnazione() {
		return dataAssegnazione;
	}
	public void setDataAssegnazione(Date dataAssegnazione) {
		this.dataAssegnazione = dataAssegnazione;
	}
	public Date getDataRipristino() {
		return dataRipristino;
	}
	public void setDataRipristino(Date dataRipristino) {
		this.dataRipristino = dataRipristino;
	}
	public Date getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	public String getChiusura() {
		return chiusura;
	}
	public void setChiusura(String chiusura) {
		this.chiusura = chiusura;
	}
	public String getProcedureManutenzione() {
		return procedureManutenzione;
	}
	public void setProcedureManutenzione(String procedureManutenzione) {
		this.procedureManutenzione = procedureManutenzione;
	}
	public String getElementiCritici() {
		return elementiCritici;
	}
	public void setElementiCritici(String elementiCritici) {
		this.elementiCritici = elementiCritici;
	}
	public String getListaStrumentiCritici() {
		return listaStrumentiCritici;
	}
	public void setListaStrumentiCritici(String listaStrumentiCritici) {
		this.listaStrumentiCritici = listaStrumentiCritici;
	}
	public String getAttivitaPED() {
		return attivitaPED;
	}
	public void setAttivitaPED(String attivitaPED) {
		this.attivitaPED = attivitaPED;
	}
	public String getAttivitaATEX() {
		return attivitaATEX;
	}
	public void setAttivitaATEX(String attivitaATEX) {
		this.attivitaATEX = attivitaATEX;
	}
	public String getPratiche() {
		return pratiche;
	}
	public void setPratiche(String pratiche) {
		this.pratiche = pratiche;
	}
	public String getVerificaPSV() {
		return verificaPSV;
	}
	public void setVerificaPSV(String verificaPSV) {
		this.verificaPSV = verificaPSV;
	}
	public String getAggiornamentoPianoIspezioni() {
		return aggiornamentoPianoIspezioni;
	}
	public void setAggiornamentoPianoIspezioni(String aggiornamentoPianoIspezioni) {
		this.aggiornamentoPianoIspezioni = aggiornamentoPianoIspezioni;
	}
	public String getInfoManutenzione() {
		return infoManutenzione;
	}
	public void setInfoManutenzione(String infoManutenzione) {
		this.infoManutenzione = infoManutenzione;
	}
	public String getAltroMai() {
		return altroMai;
	}
	public void setAltroMai(String altroMai) {
		this.altroMai = altroMai;
	}
	public String getAutorizzazioniAmbientali() {
		return autorizzazioniAmbientali;
	}
	public void setAutorizzazioniAmbientali(String autorizzazioniAmbientali) {
		this.autorizzazioniAmbientali = autorizzazioniAmbientali;
	}
	public String getIsIdoneaAttrezzaturaAntincendio() {
		return isIdoneaAttrezzaturaAntincendio;
	}
	public void setIsIdoneaAttrezzaturaAntincendio(String isIdoneaAttrezzaturaAntincendio) {
		this.isIdoneaAttrezzaturaAntincendio = isIdoneaAttrezzaturaAntincendio;
	}
	public String getDssc() {
		return dssc;
	}
	public void setDssc(String dssc) {
		this.dssc = dssc;
	}
	public String getAnalisiOperativaRischio() {
		return analisiOperativaRischio;
	}
	public void setAnalisiOperativaRischio(String analisiOperativaRischio) {
		this.analisiOperativaRischio = analisiOperativaRischio;
	}
	public String getAnalisiAspettiAmbientali() {
		return analisiAspettiAmbientali;
	}
	public void setAnalisiAspettiAmbientali(String analisiAspettiAmbientali) {
		this.analisiAspettiAmbientali = analisiAspettiAmbientali;
	}
	public String getRevisioneRischiProcesso() {
		return revisioneRischiProcesso;
	}
	public void setRevisioneRischiProcesso(String revisioneRischiProcesso) {
		this.revisioneRischiProcesso = revisioneRischiProcesso;
	}
	public String getProcedureSGI() {
		return procedureSGI;
	}
	public void setProcedureSGI(String procedureSGI) {
		this.procedureSGI = procedureSGI;
	}
	public String getRevisionePEI() {
		return revisionePEI;
	}
	public void setRevisionePEI(String revisionePEI) {
		this.revisionePEI = revisionePEI;
	}
	public String getAltroSsa() {
		return altroSsa;
	}
	public void setAltroSsa(String altroSsa) {
		this.altroSsa = altroSsa;
	}
	public String getAggiornamentoManualiOperativi() {
		return aggiornamentoManualiOperativi;
	}
	public void setAggiornamentoManualiOperativi(String aggiornamentoManualiOperativi) {
		this.aggiornamentoManualiOperativi = aggiornamentoManualiOperativi;
	}
	public String getFormazione() {
		return formazione;
	}
	public void setFormazione(String formazione) {
		this.formazione = formazione;
	}
	public String getAddestramentoOperatori() {
		return addestramentoOperatori;
	}
	public void setAddestramentoOperatori(String addestramentoOperatori) {
		this.addestramentoOperatori = addestramentoOperatori;
	}
	public String getAggiornamentoInformaticoSchedeSicurezza() {
		return aggiornamentoInformaticoSchedeSicurezza;
	}
	public void setAggiornamentoInformaticoSchedeSicurezza(String aggiornamentoInformaticoSchedeSicurezza) {
		this.aggiornamentoInformaticoSchedeSicurezza = aggiornamentoInformaticoSchedeSicurezza;
	}
	public String getAltroAdd() {
		return altroAdd;
	}
	public void setAltroAdd(String altroAdd) {
		this.altroAdd = altroAdd;
	}
	public String getClassificazioneAree() {
		return classificazioneAree;
	}
	public void setClassificazioneAree(String classificazioneAree) {
		this.classificazioneAree = classificazioneAree;
	}
	public String getProcessFlowDiagram() {
		return processFlowDiagram;
	}
	public void setProcessFlowDiagram(String processFlowDiagram) {
		this.processFlowDiagram = processFlowDiagram;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getSchemiElettrostrumentali() {
		return schemiElettrostrumentali;
	}
	public void setSchemiElettrostrumentali(String schemiElettrostrumentali) {
		this.schemiElettrostrumentali = schemiElettrostrumentali;
	}
	public String getSchemiElettrici() {
		return schemiElettrici;
	}
	public void setSchemiElettrici(String schemiElettrici) {
		this.schemiElettrici = schemiElettrici;
	}
	public String getRegistroAllarmi() {
		return registroAllarmi;
	}
	public void setRegistroAllarmi(String registroAllarmi) {
		this.registroAllarmi = registroAllarmi;
	}
	public String getRegistroBlocchi() {
		return registroBlocchi;
	}
	public void setRegistroBlocchi(String registroBlocchi) {
		this.registroBlocchi = registroBlocchi;
	}
	public String getSistemiControllo() {
		return sistemiControllo;
	}
	public void setSistemiControllo(String sistemiControllo) {
		this.sistemiControllo = sistemiControllo;
	}
	public String getAggiornamentoPLC() {
		return aggiornamentoPLC;
	}
	public void setAggiornamentoPLC(String aggiornamentoPLC) {
		this.aggiornamentoPLC = aggiornamentoPLC;
	}
	public String getAggiornamentoDCS() {
		return aggiornamentoDCS;
	}
	public void setAggiornamentoDCS(String aggiornamentoDCS) {
		this.aggiornamentoDCS = aggiornamentoDCS;
	}
	public String getAltroAdr() {
		return altroAdr;
	}
	public void setAltroAdr(String altroAdr) {
		this.altroAdr = altroAdr;
	}
	public String getPraticheFiscali() {
		return praticheFiscali;
	}
	public void setPraticheFiscali(String praticheFiscali) {
		this.praticheFiscali = praticheFiscali;
	}
	public String getUnmig() {
		return unmig;
	}
	public void setUnmig(String unmig) {
		this.unmig = unmig;
	}
	public String getAltroVarie() {
		return altroVarie;
	}
	public void setAltroVarie(String altroVarie) {
		this.altroVarie = altroVarie;
	}

}
