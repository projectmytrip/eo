package com.eni.ioc.assetintegrity.dto;

import java.io.Serializable;
import java.util.Date;

public class CondizioneAnomala implements Serializable {

	private static final long serialVersionUID = 7343463830530192243L;

	private Long id;
	
	private Date t;

    private VariazioneKpi variazioneKpi;

    private SogliaKpi sogliaSforata;

    private String stato;
	
	private Date dataInserimento = new Date();

	private Date dataModifica;

	public CondizioneAnomala() {
		this.stato = "APERTA";
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}


	public VariazioneKpi getVariazioneKpi() {
		return variazioneKpi;
	}

	public void setVariazioneKpi(VariazioneKpi variazioneKpi) {
		this.variazioneKpi = variazioneKpi;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public SogliaKpi getSogliaSforata() {
		return sogliaSforata;
	}

	public void setSogliaSforata(SogliaKpi sogliaSforata) {
		this.sogliaSforata = sogliaSforata;
	}

	
	public Date getT() {
		return t;
	}

	public void setT(Date t) {
		this.t = t;
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
	
	public boolean correlata(VariazioneKpi variazioneKpi) {
		return this.sogliaSforata.correlata(variazioneKpi);
	}
	
	public String getLevel() {
		return this.sogliaSforata.getLevel();
	}

	@Override
	public String toString() {
		return "EntitaCondizioneAnomala [id=" + id + ", t=" + t + ", variazioneKpi=" + variazioneKpi + ", sogliaSforata="
				+ sogliaSforata + ", stato=" + stato + ", dataInserimento=" + dataInserimento + ", dataModifica="
				+ dataModifica + "]";
	}

	
	

}
