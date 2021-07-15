package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EVPMSAlertDto {

    private String AlertID;
    private String Chainage;
    private String Latitude;
    private String Longitude;    
    private String AlertDate;
    private String AlertType;
    private String IDStation;

    public String getAlertID() {
        return AlertID;
    }

    public void setAlertID(String AlertID) {
        this.AlertID = AlertID;
    }

    public String getChainage() {
        return Chainage;
    }

    public void setChainage(String Chainage) {
        this.Chainage = Chainage;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getAlertDate() {
        return AlertDate;
    }

    public void setAlertDate(String AlertDate) {
        this.AlertDate = AlertDate;
    }

    public String getAlertType() {
        return AlertType;
    }

    public void setAlertType(String AlertType) {
        this.AlertType = AlertType;
    }

    public String getIDStation() {
        return IDStation;
    }

    public void setIDStation(String IDStation) {
        this.IDStation = IDStation;
    }
    
}
