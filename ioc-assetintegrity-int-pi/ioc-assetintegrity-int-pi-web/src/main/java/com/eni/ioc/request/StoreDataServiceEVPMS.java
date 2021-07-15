package com.eni.ioc.request;

import com.eni.ioc.pi.rest.response.EVPMSAlertDto;
import com.eni.ioc.pi.rest.response.EVPMSStationDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Asset",
    "Stations",
    "Alerts"
})
public class StoreDataServiceEVPMS implements Serializable {

    @JsonProperty("Asset")
    private String asset;
    @JsonProperty("Stations")
    private List<EVPMSStationDto> stations;
    @JsonProperty("Alerts")
    private List<EVPMSAlertDto> alerts;

    @JsonProperty("Asset")
    public String getAsset() {
        return asset;
    }

    @JsonProperty("Asset")
    public void setAsset(String asset) {
        this.asset = asset;
    }

    public List<EVPMSStationDto> getStations() {
        return stations;
    }

    public void setStations(List<EVPMSStationDto> stations) {
        this.stations = stations;
    }

    public List<EVPMSAlertDto> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<EVPMSAlertDto> alerts) {
        this.alerts = alerts;
    }

}
