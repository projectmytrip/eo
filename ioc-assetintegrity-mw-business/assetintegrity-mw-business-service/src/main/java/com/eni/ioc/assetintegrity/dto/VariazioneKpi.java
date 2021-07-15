package com.eni.ioc.assetintegrity.dto;

import java.io.Serializable;
import java.util.Date;

public class VariazioneKpi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1433981118298433326L;

	private Long id;

	private Date t;
	
	private String asset;

	private String vista;
	
	private Long idKpiSistemaSorgente;

	private String nome;
	
	private String stazione;

	private String level;

	private Double valore;

	private String unitaMisura;

	private Date dataInserimento = new Date();

	private Date dataModifica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getT() {
		return t;
	}

	public void setT(Date t) {
		this.t = t;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public Long getIdKpiSistemaSorgente() {
		return idKpiSistemaSorgente;
	}

	public void setIdKpiSistemaSorgente(Long idKpiSistemaSorgente) {
		this.idKpiSistemaSorgente = idKpiSistemaSorgente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValore() {
		return valore;
	}

	public void setValore(Double valore) {
		this.valore = valore;
	}

	public String getUnitaMisura() {
		return unitaMisura;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public String getStazione() {
		return stazione;
	}

	public void setStazione(String stazione) {
		this.stazione = stazione;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "VariazioneKpi [id=" + id + ", t=" + t + ", asset=" + asset + ", vista=" + vista
				+ ", idKpiSistemaSorgente=" + idKpiSistemaSorgente + ", nome=" + nome + ", stazione=" + stazione
				+ ", level=" + level + ", valore=" + valore + ", unitaMisura=" + unitaMisura + ", dataInserimento="
				+ dataInserimento + ", dataModifica=" + dataModifica + "]";
	}


}
