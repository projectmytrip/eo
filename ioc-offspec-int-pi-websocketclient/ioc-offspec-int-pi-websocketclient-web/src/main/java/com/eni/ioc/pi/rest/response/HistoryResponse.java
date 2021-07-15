
package com.eni.ioc.pi.rest.response;

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
    "Links",
    "Items",
    "UnitsAbbreviation"
})
public class HistoryResponse implements Serializable
{

    @JsonProperty("Links")
    private Links links;
    @JsonProperty("Items")
    private List<Item> items = null;
    @JsonProperty("UnitsAbbreviation")
    private String unitsAbbreviation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5598869000699028312L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HistoryResponse() {
    }

    /**
     * 
     * @param items
     * @param links
     * @param unitsAbbreviation
     */
    public HistoryResponse(Links links, List<Item> items, String unitsAbbreviation) {
        super();
        this.links = links;
        this.items = items;
        this.unitsAbbreviation = unitsAbbreviation;
    }

    @JsonProperty("Links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("Links")
    public void setLinks(Links links) {
        this.links = links;
    }

    public HistoryResponse withLinks(Links links) {
        this.links = links;
        return this;
    }

    @JsonProperty("Items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("Items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public HistoryResponse withItems(List<Item> items) {
        this.items = items;
        return this;
    }

    @JsonProperty("UnitsAbbreviation")
    public String getUnitsAbbreviation() {
        return unitsAbbreviation;
    }

    @JsonProperty("UnitsAbbreviation")
    public void setUnitsAbbreviation(String unitsAbbreviation) {
        this.unitsAbbreviation = unitsAbbreviation;
    }

    public HistoryResponse withUnitsAbbreviation(String unitsAbbreviation) {
        this.unitsAbbreviation = unitsAbbreviation;
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

    public HistoryResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
