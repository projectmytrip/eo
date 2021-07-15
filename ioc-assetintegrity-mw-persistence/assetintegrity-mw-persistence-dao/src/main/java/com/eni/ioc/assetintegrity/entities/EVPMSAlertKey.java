package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EVPMSAlertKey implements Serializable {
    
    @Column(name = "ALERT_ID")
    private String AlertID;
    @Column(name = "ALERT_DATE")
    private LocalDateTime AlertDate;

    public String getAlertID() {
        return AlertID;
    }

    public void setAlertID(String AlertID) {
        this.AlertID = AlertID;
    }

    public LocalDateTime getAlertDate() {
        return AlertDate;
    }

    public void setAlertDate(LocalDateTime AlertDate) {
        this.AlertDate = AlertDate;
    }
    
    
}
