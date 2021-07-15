package com.eni.ioc.assetintegrity.dto;

public class KpiDataDto {

    private String id;
    private String valore;
    private String stato;
    private String ultimoUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getUltimoUpdate() {
        return ultimoUpdate;
    }

    public void setUltimoUpdate(String ultimoUpdate) {
        this.ultimoUpdate = ultimoUpdate;
    }

    
}
