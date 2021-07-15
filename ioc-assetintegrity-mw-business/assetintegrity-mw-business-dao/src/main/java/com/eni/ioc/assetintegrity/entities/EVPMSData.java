package com.eni.ioc.assetintegrity.entities;

public class EVPMSData {
    
    private String dorsal;
    private String station;
    private Integer status;
    private Boolean diagnostic;
    private Boolean leakDetection;
    private String chainage;
    private String longitude;
    private String latitude;    
    private String eventDate;
    private String type;

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean isDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Boolean diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Boolean isLeakDetection() {
        return leakDetection;
    }

    public void setLeakDetection(Boolean leakDetection) {
        this.leakDetection = leakDetection;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChainage() {
        return chainage;
    }

    public void setChainage(String chainage) {
        this.chainage = chainage;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Boolean getDiagnostic() {
        return diagnostic;
    }

    public Boolean getLeakDetection() {
        return leakDetection;
    }
    
}
