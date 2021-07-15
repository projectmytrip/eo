package com.eni.ioc.assetintegrity.dto;

import java.util.Date;

import com.eni.ioc.assetintegrity.entities.RegistroDe;

public class RegistroDeDto {

	private Long id;
    private String tipo;
    private String severity;
    private String area;
    private String unita;
    private String numeroDe;
    private String descrizione;
    private String motivo;
    private String durata;
    private String permessi;
    private Date dataApertura;
    private Date dataChiusura;
    private Date dataMax;
    private String durataAttivita;
    private String note;
    private String hashKey;
    private String wfId;
    private String wfDetail;
    private String asset;

    public RegistroDeDto() {
        // stub
    }

    public RegistroDeDto(RegistroDe de) {
        if(de != null){
        	setId(de.getId());
            setTipo(de.getTipo());
            setSeverity(de.getSeverity());
            setArea(de.getArea());
            setUnita(de.getUnita());
            setNumeroDe(de.getNumeroDe());
            setDescrizione(de.getDescrizione());
            setMotivo(de.getMotivo());
            setDurata(de.getDurata());
            setPermessi(de.getPermessi());
            setDataApertura(de.getDataApertura());
            setDataChiusura(de.getDataChiusura());
            setDataMax(de.getDataMax());
            setDurataAttivita(de.getDurataAttivita());
            setNote(de.getNote());
            setHashKey(de.getHashKey());
            setWfId(de.getWfId());
            setWfDetail(de.getWfDetail());
            setAsset(de.getAsset());
        }
    }

    public Long getId() {
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}

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

    public void setUnita(String unità) {
        this.unita = unità;
    }

    public String getNumeroDe() {
        return numeroDe;
    }

    public void setNumeroDe(String numeroDe) {
        this.numeroDe = numeroDe;
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

    public Date getDataApertura() {
        return dataApertura;
    }

    public void setDataApertura(Date dataApertura) {
        this.dataApertura = dataApertura;
    }

    public Date getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(Date dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public Date getDataMax() {
        return dataMax;
    }

    public void setDataMax(Date dataMax) {
        this.dataMax = dataMax;
    }

    public String getDurataAttivita() {
        return durataAttivita;
    }

    public void setDurataAttivita(String durataAttivita) {
        this.durataAttivita = durataAttivita;
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

    public String getWfDetail() {
        return wfDetail;
    }

    public void setWfDetail(String wfDetail) {
        this.wfDetail = wfDetail;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }
}