package com.eni.ioc.assetintegrity.dto.storedataservice;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EVPMSStationDto {

    private String  WellArea;
    private String  StationName;
    private String  Dorsal;
    private String  IDStation;
    private Boolean AcPresence;
    private Boolean Acquisition;
    private Boolean Dejitter;
    private Boolean Gps;
    private Boolean Gsm;
    private Boolean Intrusion;
    private Boolean UsbConnection;

    public String getWellArea() {
        return WellArea;
    }

    public void setWellArea(String WellArea) {
        this.WellArea = WellArea;
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

    public String getIDStation() {
        return IDStation;
    }

    public void setIDStation(String IDStation) {
        this.IDStation = IDStation;
    }
  
}
