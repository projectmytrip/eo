package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DE_REGISTRY")
public class RegistroDe implements Serializable {

    private static final long serialVersionUID = 0L;

	// key: string to show in spreadsheet cell
	// value: method to get value
    public static Map<String, String> excelOrder = new LinkedHashMap<>();


    static {
    	excelOrder.put("Item N°", "getId");
        excelOrder.put("Tipo (operativa / manutentiva)", "getTipo");
        excelOrder.put("Classificazione (tipo 1, 2 o 3; Emergenza, Non SCE))", "getSeverity");
        excelOrder.put("Area", "getArea");
        excelOrder.put("Unità funzionale", "getUnita");
        excelOrder.put("Autorizzazione N°", "getNumeroDe");
        excelOrder.put("Descrizione", "getDescrizione");
        excelOrder.put("Motivo dell'esclusione / disattivazione", "getMotivo");
        excelOrder.put("Durata", "getDurata");
        excelOrder.put("numero/i Permesso di Lavoro", "getPermessi");
        excelOrder.put("Data Apertura Autorizzazione", "getDataApertura");
        excelOrder.put("Data Chiusura Autorizzazione", "getDataChiusura");
        excelOrder.put("Data Max", "getDataMax");
        excelOrder.put("gg attività D/E", "getDurataAttivita");
        excelOrder.put("Commenti", "getNote");
       // excelOrder.put("HASHKEY", "getHashKey");
       // excelOrder.put("WFID", "getWfId");
       // excelOrder.put("WFDETAIL", "getWfDetail");
       // excelOrder.put("WFIDRIPRISTINO", "getWfRipristino");
       // excelOrder.put("ASSET", "getAsset");
    }

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "SEVERITY")
    private String severity;

    @Column(name = "AREA")
    private String area;

    @Column(name = "UNITA")
    private String unita;

    @Column(name = "NUMERO_DE")
    private String numeroDe;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    @Column(name = "MOTIVO")
    private String motivo;

    @Column(name = "DURATA")
    private String durata;

    @Column(name = "PERMESSI")
    private String permessi;

    @Column(name = "DATA_APERTURA")
    private Date dataApertura;

    @Column(name = "DATA_CHIUSURA")
    private Date dataChiusura;

    @Column(name = "DATA_MAX")
    private Date dataMax;

    @Column(name = "DURATA_ATTIVITA")
    private String durataAttivita;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "HASH_KEY")
    private String hashKey;

    @Column(name = "WF_ID")
    private String wfId;

    @Column(name = "WORKFLOW_DETAIL")
    private String wfDetail;

    @Column(name = "ASSET")
    private String asset; 

    @Column(name = "WF_RIPRISTINO")
    private String wfRipristino;

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getSeverity() { return severity; }

    public void setSeverity(String severity) { this.severity = severity; }

    public String getArea() { return area; }

    public void setArea(String area) { this.area = area; }

    public String getUnita() { return unita; }

    public void setUnita(String unita) { this.unita = unita; }

    public String getNumeroDe() { return numeroDe; }

    public void setNumeroDe(String numeroDe) { this.numeroDe = numeroDe; }

    public String getDescrizione() { return descrizione; }

    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getMotivo() { return motivo; }

    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getDurata() { return durata; }

    public void setDurata(String durata) { this.durata = durata; }

    public String getPermessi() { return permessi; }

    public void setPermessi(String permessi) { this.permessi = permessi; }

    public Date getDataApertura() { return dataApertura; }

    public void setDataApertura(Date dataApertura) { this.dataApertura = dataApertura; }

    public Date getDataChiusura() { return dataChiusura; }

    public void setDataChiusura(Date dataChiusura) { this.dataChiusura = dataChiusura; }

    public Date getDataMax() { return dataMax; }

    public void setDataMax(Date dataMax) { this.dataMax = dataMax; }

    public String getDurataAttivita() { return durataAttivita; }

    public void setDurataAttivita(String durataAttivita) { this.durataAttivita = durataAttivita; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public String getHashKey() { return hashKey; }

    public void setHashKey(String hashKey) { this.hashKey = hashKey; }

    public String getWfId() { return wfId; }

    public void setWfId(String wfId) { this.wfId = wfId; }

    public String getWfDetail() { return wfDetail; }

    public void setWfDetail(String wfDetail) { this.wfDetail = wfDetail; }

    public String getAsset() { return asset; }

    public void setAsset(String asset) { this.asset = asset; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getWfRipristino() {
		return wfRipristino;
	}
    
    public void setWfRipristino(String wfRipristino) {
		this.wfRipristino = wfRipristino;
	}
}
