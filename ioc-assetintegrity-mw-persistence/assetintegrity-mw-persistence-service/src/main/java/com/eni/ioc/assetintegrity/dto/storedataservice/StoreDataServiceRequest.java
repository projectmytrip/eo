package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Asset",
    "Element",
    "LastBatch"
})
public class StoreDataServiceRequest<T> implements Serializable {
    
    @JsonProperty("Asset")
    private String asset;
    @JsonProperty("Element")
    private List<T> element = null;
    @JsonProperty("LastBatch")
    private Boolean lastInBatch;

    @JsonProperty("Asset")
    public String getAsset() {
        return asset;
    }

    @JsonProperty("Asset")
    public void setAsset(String asset) {
        this.asset = asset;
    }

    public StoreDataServiceRequest withAsset(String asset) {
        this.asset = asset;
        return this;
    }

    @JsonProperty("Element")
    public List<T> getElement() {
        return element;
    }

    @JsonProperty("Element")
    public void setElement(List<T> element) {
        this.element = element;
    }
    
    @JsonProperty("LastBatch")
    public Boolean getLastInBatch() {
		return lastInBatch;
	}
    
    @JsonProperty("LastBatch")
    public void setLastInBatch(Boolean lastInBatch) {
		this.lastInBatch = lastInBatch;
	}

}
