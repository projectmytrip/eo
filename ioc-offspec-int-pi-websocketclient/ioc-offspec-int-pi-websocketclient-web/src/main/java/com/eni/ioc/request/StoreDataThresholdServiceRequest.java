
package com.eni.ioc.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Asset",
    "Element"
})
public class StoreDataThresholdServiceRequest implements Serializable
{

    @JsonProperty("Asset")
    private String asset;
    @JsonProperty("Element")
    private List<Threshold> element = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 4620379510248555928L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StoreDataThresholdServiceRequest() {
    }

    /**
     * 
     * @param element
     * @param asset
     * @param actualValue
     */
    public StoreDataThresholdServiceRequest(String asset, List<Threshold> element) {
        super();
        this.asset = asset;
        this.element = element;
    }

    @JsonProperty("Asset")
    public String getAsset() {
        return asset;
    }

    @JsonProperty("Asset")
    public void setAsset(String asset) {
        this.asset = asset;
    }

    public StoreDataThresholdServiceRequest withAsset(String asset) {
        this.asset = asset;
        return this;
    }

    @JsonProperty("Element")
    public List<Threshold> getElement() {
        return element;
    }

    @JsonProperty("Element")
    public void setElement(List<Threshold> element) {
        this.element = element;
    }

    public StoreDataThresholdServiceRequest withElement(List<Threshold> element) {
        this.element = element;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public StoreDataThresholdServiceRequest withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
