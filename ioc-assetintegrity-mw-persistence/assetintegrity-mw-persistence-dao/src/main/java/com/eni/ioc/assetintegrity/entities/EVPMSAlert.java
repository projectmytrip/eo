package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVPMS_ALERTS")
public class EVPMSAlert {

    @Id
    private EVPMSAlertKey alertKey;
    @Column(name = "CHAINAGE")
    private String Chainage;
    @Column(name = "LATITUDE")
    private String Latitude;
    @Column(name = "LONGITUDE")
    private String Longitude;
    @Column(name = "ALERT_TYPE")
    private String AlertType;
    @Column(name = "STATION_ID")
    private String IDStation;
    @Column(name = "ASSET")
    private String asset;
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;

    public EVPMSAlert() {
        super();
        this.alertKey = new EVPMSAlertKey();
    }
    
    public EVPMSAlertKey getAlertKey() {
        return alertKey;
    }

    public void setAlertKey(EVPMSAlertKey alertKey) {
        this.alertKey = alertKey;
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

    public LocalDateTime getAlertDate() {
        return this.alertKey.getAlertDate();
    }

    public void setAlertDate(LocalDateTime AlertDate) {
        this.alertKey.setAlertDate(AlertDate);
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
