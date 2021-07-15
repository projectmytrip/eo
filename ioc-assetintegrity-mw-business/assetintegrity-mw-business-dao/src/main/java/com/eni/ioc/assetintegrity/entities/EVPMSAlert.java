package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVPMS_ALERTS")
public class EVPMSAlert {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ALERT_ID")
    private String AlertID;
    @Column(name = "CHAINAGE")
    private String Chainage;
    @Column(name = "LATITUDE")
    private String Latitude;
    @Column(name = "LONGITUDE")
    private String Longitude;
    @Column(name = "ALERT_DATE")
    private LocalDateTime AlertDate;
    @Column(name = "ALERT_TYPE")
    private String AlertType;
    @Column(name = "STATION_ID")
    private String IDStation;
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

    public LocalDateTime getAlertDate() {
        return AlertDate;
    }

    public void setAlertDate(LocalDateTime AlertDate) {
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
