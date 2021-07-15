package com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities;

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
    "Name",
    "DetailElement"
})
public class Element implements Serializable
{

    @JsonProperty("Name")
    private String name;
    @JsonProperty("DetailElement")
    private List<DetailElement> detailElement = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7749988543072922972L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Element() {
    }

    /**
     * 
     * @param detailElement
     * @param name
     */
    public Element(String name, List<DetailElement> detailElement) {
        super();
        this.name = name;
        this.detailElement = detailElement;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public Element withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("DetailElement")
    public List<DetailElement> getDetailElement() {
        return detailElement;
    }

    @JsonProperty("DetailElement")
    public void setDetailElement(List<DetailElement> detailElement) {
        this.detailElement = detailElement;
    }

    public Element withDetailElement(List<DetailElement> detailElement) {
        this.detailElement = detailElement;
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

    public Element withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
