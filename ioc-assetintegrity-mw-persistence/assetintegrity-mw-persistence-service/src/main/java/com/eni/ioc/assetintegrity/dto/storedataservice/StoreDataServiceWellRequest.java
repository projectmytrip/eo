package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Asset",
    "Element"
})
public class StoreDataServiceWellRequest implements Serializable {

    private static final long serialVersionUID = 4721801996223449128L;
    
    @JsonProperty("Asset")	
    private String asset;
    @JsonProperty("Element")
    private List<WellAlarmDto> element = null;

    public StoreDataServiceWellRequest() {
    }

    public StoreDataServiceWellRequest(String asset, List<WellAlarmDto> element) {
        super();
        this.asset = asset;
        this.element = element;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public List<WellAlarmDto> getElement() {
        return element;
    }

    public void setElement(List<WellAlarmDto> element) {
        this.element = element;
    }

    public StoreDataServiceWellRequest withElement(List<WellAlarmDto> element) {
        this.element = element;
        return this;
    }
}
