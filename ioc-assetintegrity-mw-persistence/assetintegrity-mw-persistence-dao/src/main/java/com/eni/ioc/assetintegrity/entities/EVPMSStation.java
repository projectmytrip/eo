package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVPMS_STATIONS")
public class EVPMSStation {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "STATION_ID")
    private String stationId;
    @Column(name = "STATION_NAME")
    private String StationName;
    @Column(name = "DORSAL")
    private String Dorsal;
    @Column(name = "AC_PRESENCE")
    private Boolean AcPresence;
    @Column(name = "ACQUISITION")
    private Boolean Acquisition;
    @Column(name = "DEJITTER")
    private Boolean Dejitter;
    @Column(name = "GPS")
    private Boolean Gps;
    @Column(name = "GSM")
    private Boolean Gsm;
    @Column(name = "INTRUSION")
    private Boolean Intrusion;
    @Column(name = "USB_CONNECTION")
    private Boolean UsbConnection;
    @Column(name = "AREA")
    private String area;
    @Column(name = "ASSET")
    private String asset;
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public String getDorsal() {
        return Dorsal;
    }

    public void setDorsal(String Dorsal) {
        this.Dorsal = Dorsal;
    }

    public Boolean getAcPresence() {
        return AcPresence;
    }

    public void setAcPresence(Boolean AcPresence) {
        this.AcPresence = AcPresence;
    }

    public Boolean getAcquisition() {
        return Acquisition;
    }

    public void setAcquisition(Boolean Acquisition) {
        this.Acquisition = Acquisition;
    }

    public Boolean getDejitter() {
        return Dejitter;
    }

    public void setDejitter(Boolean Dejitter) {
        this.Dejitter = Dejitter;
    }

    public Boolean getGps() {
        return Gps;
    }

    public void setGps(Boolean Gps) {
        this.Gps = Gps;
    }

    public Boolean getGsm() {
        return Gsm;
    }

    public void setGsm(Boolean Gsm) {
        this.Gsm = Gsm;
    }

    public Boolean getIntrusion() {
        return Intrusion;
    }

    public void setIntrusion(Boolean Intrusion) {
        this.Intrusion = Intrusion;
    }

    public Boolean getUsbConnection() {
        return UsbConnection;
    }

    public void setUsbConnection(Boolean UsbConnection) {
        this.UsbConnection = UsbConnection;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public LocalDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }
    
    
}
