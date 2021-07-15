package com.eni.ioc.assetintegrity.dto;

import java.io.Serializable;
import java.util.Date;

public class SogliaKpi implements Serializable {

	private static final long serialVersionUID = -5076439771929994612L;

	private Long id;

	private String codice;
	
	private String asset;
	
	private String vista;

	private String kpi;

	private String label;

	private String level;

	private String levelType;

	private Double sogliaInferiore;

	private Double sogliaSuperiore;

	private String descrizione;

	private String timeTrueStr;

	private String timeTrueBackStr;

	private String codiceProcessoWorkflowAperturaDaChiamare;

	private String codiceProcessoWorkflowChiusuraDaChiamare;

	private Date dataInserimento = new Date();

	private Date dataModifica;

	private int daGestireConWorkflow;
	
	private int daGestireConCondizioneAnomala;
	
	private String unitaMisura;
	
	private String stazione;

	public SogliaKpi() {
		//
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public boolean correlata(SogliaKpi sogliaKpi) {
		return
				this.getKpi().equals( sogliaKpi.getKpi() )
				&& this.getVista().equals( sogliaKpi.getVista() )
				&& this.getAsset().equals( sogliaKpi.getAsset() )
				;
	}

	public boolean correlata(VariazioneKpi variazioneKpi) {
		return
				this.getKpi().equals( variazioneKpi.getNome() )
				&& this.getVista().equals( variazioneKpi.getVista() )
				&& this.getAsset().equals( variazioneKpi.getAsset() )
				;
	}

	
	public String getUnitaMisura() {
		return unitaMisura;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getKpi() {
		return kpi;
	}

	public void setKpi(String kpi) {
		this.kpi = kpi;
	}

	public Double getSogliaInferiore() {
		return sogliaInferiore;
	}

	public void setSogliaInferiore(Double sogliaInferiore) {
		this.sogliaInferiore = sogliaInferiore;
	}

	public Double getSogliaSuperiore() {
		return sogliaSuperiore;
	}

	public void setSogliaSuperiore(Double sogliaSuperiore) {
		this.sogliaSuperiore = sogliaSuperiore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodiceProcessoWorkflowAperturaDaChiamare() {
		return codiceProcessoWorkflowAperturaDaChiamare;
	}

	public void setCodiceProcessoWorkflowAperturaDaChiamare(String codiceProcessoWorkflowAperturaDaChiamare) {
		this.codiceProcessoWorkflowAperturaDaChiamare = codiceProcessoWorkflowAperturaDaChiamare;
	}

	public String getCodiceProcessoWorkflowChiusuraDaChiamare() {
		return codiceProcessoWorkflowChiusuraDaChiamare;
	}

	public void setCodiceProcessoWorkflowChiusuraDaChiamare(String codiceProcessoWorkflowChiusuraDaChiamare) {
		this.codiceProcessoWorkflowChiusuraDaChiamare = codiceProcessoWorkflowChiusuraDaChiamare;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getTimeTrueStr() {
		return timeTrueStr;
	}

	public void setTimeTrueStr(String timeTrueStr) {
		this.timeTrueStr = timeTrueStr;
	}

	public String getTimeTrueBackStr() {
		return timeTrueBackStr;
	}

	public void setTimeTrueBackStr(String timeTrueBackStr) {
		this.timeTrueBackStr = timeTrueBackStr;
	}

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public boolean isDaGestireConWorkflow() {
		return daGestireConWorkflow==1;
	}

	public void setDaGestireConWorkflow(boolean daGestireConWorkflow) {
		if ( daGestireConWorkflow ) {
			this.daGestireConWorkflow = 1;
		} else {
			this.daGestireConWorkflow = 0;
		}
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public boolean isDaGestireConCondizioneAnomala() {
		return daGestireConCondizioneAnomala==1;
	}

	public void setDaGestireConCondizioneAnomala(boolean daGestireConCondizioneAnomala) {
		if ( daGestireConCondizioneAnomala ) {
			this.daGestireConCondizioneAnomala = 1;
		} else {
			this.daGestireConCondizioneAnomala = 0;
		}
	}

	public int hashCode() {
		return asset.hashCode()+vista.hashCode()+kpi.hashCode()+level.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	public int getDaGestireConWorkflow() {
		return daGestireConWorkflow;
	}

	public void setDaGestireConWorkflow(int daGestireConWorkflow) {
		this.daGestireConWorkflow = daGestireConWorkflow;
	}

	public int getDaGestireConCondizioneAnomala() {
		return daGestireConCondizioneAnomala;
	}

	public void setDaGestireConCondizioneAnomala(int daGestireConCondizioneAnomala) {
		this.daGestireConCondizioneAnomala = daGestireConCondizioneAnomala;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "SogliaKpi [id=" + id + ", codice=" + codice + ", asset=" + asset + ", vista=" + vista + ", kpi=" + kpi
				+ ", label=" + label + ", level=" + level + ", levelType=" + levelType + ", sogliaInferiore="
				+ sogliaInferiore + ", sogliaSuperiore=" + sogliaSuperiore + ", descrizione=" + descrizione
				+ ", timeTrueStr=" + timeTrueStr + ", timeTrueBackStr=" + timeTrueBackStr
				+ ", codiceProcessoWorkflowAperturaDaChiamare=" + codiceProcessoWorkflowAperturaDaChiamare
				+ ", codiceProcessoWorkflowChiusuraDaChiamare=" + codiceProcessoWorkflowChiusuraDaChiamare
				+ ", dataInserimento=" + dataInserimento + ", dataModifica=" + dataModifica + ", daGestireConWorkflow="
				+ daGestireConWorkflow + ", daGestireConCondizioneAnomala=" + daGestireConCondizioneAnomala
				+ ", unitaMisura=" + unitaMisura + "]";
	}

	public String getStazione() {
		return stazione;
	}

	public void setStazione(String stazione) {
		this.stazione = stazione;
	}

	
	
}
