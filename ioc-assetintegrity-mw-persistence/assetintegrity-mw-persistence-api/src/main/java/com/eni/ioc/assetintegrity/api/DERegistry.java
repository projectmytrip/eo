package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;

public class DERegistry implements Serializable {
	
	private static final long serialVersionUID = 0L;
	
    private String tipo; 
	private String severity;
	private String area;
	private String unita;
	private String nDe;
	private String descrizione;
	private String motivo;
	private String durata; 
    private String permessi;
    private String apertura;
    private String chiusura;
    private String max;
    private String maxDurata;
    private String note;
    private String hashKey;
    private String wfId;
	private String workflowDetail;
	private String asset;
	private String wfRipristino;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getUnita() {
		return unita;
	}
	public void setUnita(String unita) {
		this.unita = unita;
	}
	public String getnDe() {
		return nDe;
	}
	public void setnDe(String nDe) {
		this.nDe = nDe;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getDurata() {
		return durata;
	}
	public void setDurata(String durata) {
		this.durata = durata;
	}
	public String getPermessi() {
		return permessi;
	}
	public void setPermessi(String permessi) {
		this.permessi = permessi;
	}
	public String getApertura() {
		return apertura;
	}
	public void setApertura(String apertura) {
		this.apertura = apertura;
	}
	public String getChiusura() {
		return chiusura;
	}
	public void setChiusura(String chiusura) {
		this.chiusura = chiusura;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMaxDurata() {
		return maxDurata;
	}
	public void setMaxDurata(String maxDurata) {
		this.maxDurata = maxDurata;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getHashKey() {
		return hashKey;
	}
	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}
	public String getWfId() {
		return wfId;
	}
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	public String getWorkflowDetail() {
		return workflowDetail;
	}
	public void setWorkflowDetail(String workflowDetail) {
		this.workflowDetail = workflowDetail;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	
	public String getWfRipristino() {
		return wfRipristino;
	}
	
	public void setWfRipristino(String wfRipristino) {
		this.wfRipristino = wfRipristino;
	}
}
