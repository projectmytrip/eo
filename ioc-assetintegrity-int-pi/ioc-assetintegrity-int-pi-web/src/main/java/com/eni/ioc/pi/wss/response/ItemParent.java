
package com.eni.ioc.pi.wss.response;

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
    "WebId",
    "Name",
    "Path",
    "Links",
    "Items",
    "UnitsAbbreviation"
})
public class ItemParent {

    @JsonProperty("WebId")
    private String webId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Path")
    private String path;
    @JsonProperty("Links")
    private Links links;
    @JsonProperty("Items")
    private List<ItemWss> items = null;
    @JsonProperty("UnitsAbbreviation")
    private String unitsAbbreviation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ItemParent() {
    }

    /**
     * 
     * @param items
     * @param name
     * @param path
     * @param links
     * @param webId
     * @param unitsAbbreviation
     */
    public ItemParent(String webId, String name, String path, Links links, List<ItemWss> items, String unitsAbbreviation) {
        super();
        this.webId = webId;
        this.name = name;
        this.path = path;
        this.links = links;
        this.items = items;
        this.unitsAbbreviation = unitsAbbreviation;
    }

    @JsonProperty("WebId")
    public String getWebId() {
        return webId;
    }

    @JsonProperty("WebId")
    public void setWebId(String webId) {
        this.webId = webId;
    }

    public ItemParent withWebId(String webId) {
        this.webId = webId;
        return this;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public ItemParent withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("Path")
    public String getPath() {
        return path;
    }

    @JsonProperty("Path")
    public void setPath(String path) {
        this.path = path;
    }

    public ItemParent withPath(String path) {
        this.path = path;
        return this;
    }

    @JsonProperty("Links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("Links")
    public void setLinks(Links links) {
        this.links = links;
    }

    public ItemParent withLinks(Links links) {
        this.links = links;
        return this;
    }

    @JsonProperty("Items")
    public List<ItemWss> getItems() {
        return items;
    }

    @JsonProperty("Items")
    public void setItems(List<ItemWss> items) {
        this.items = items;
    }

    public ItemParent withItems(List<ItemWss> items) {
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

    public ItemParent withUnitsAbbreviation(String unitsAbbreviation) {
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

    public ItemParent withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
