package com.eni.ioc.emission.dto.soredataserviceanomaly;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Asset",
    "ActualValue",
    "Element"
})
public class StoreDataServiceAnomalyRequest implements Serializable
{

    @JsonProperty("Asset")
    private String asset;
    @JsonProperty("ActualValue")
    private boolean actualValue;
    @JsonProperty("Element")
    private List<Element> element = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 4620379510248555928L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StoreDataServiceAnomalyRequest() {
    }

    /**
     * 
     * @param element
     * @param asset
     * @param actualValue
     */
    public StoreDataServiceAnomalyRequest(String asset, boolean actualValue, List<Element> element) {
        super();
        this.asset = asset;
        this.actualValue = actualValue;
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

    public StoreDataServiceAnomalyRequest withAsset(String asset) {
        this.asset = asset;
        return this;
    }

    @JsonProperty("ActualValue")
    public boolean isActualValue() {
        return actualValue;
    }

    @JsonProperty("ActualValue")
    public void setActualValue(boolean actualValue) {
        this.actualValue = actualValue;
    }

    public StoreDataServiceAnomalyRequest withActualValue(boolean actualValue) {
        this.actualValue = actualValue;
        return this;
    }

    @JsonProperty("Element")
    public List<Element> getElement() {
        return element;
    }

    @JsonProperty("Element")
    public void setElement(List<Element> element) {
        this.element = element;
    }

    public StoreDataServiceAnomalyRequest withElement(List<Element> element) {
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

    public StoreDataServiceAnomalyRequest withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    
	public String toJson() {
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

}
