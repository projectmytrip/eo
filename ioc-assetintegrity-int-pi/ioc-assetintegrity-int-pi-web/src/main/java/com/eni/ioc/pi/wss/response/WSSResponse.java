
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
    "LinksParent",
    "ItemParent"
})
public class WSSResponse {

    @JsonProperty("LinksParent")
    private LinksParent linksParent;
    @JsonProperty("ItemParent")
    private List<ItemParent> itemParent = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public WSSResponse() {
    }

    /**
     * 
     * @param linksParent
     * @param itemParent
     */
    public WSSResponse(LinksParent linksParent, List<ItemParent> itemParent) {
        super();
        this.linksParent = linksParent;
        this.itemParent = itemParent;
    }

    @JsonProperty("LinksParent")
    public LinksParent getLinksParent() {
        return linksParent;
    }

    @JsonProperty("LinksParent")
    public void setLinksParent(LinksParent linksParent) {
        this.linksParent = linksParent;
    }

    public WSSResponse withLinksParent(LinksParent linksParent) {
        this.linksParent = linksParent;
        return this;
    }

    @JsonProperty("ItemParent")
    public List<ItemParent> getItemParent() {
        return itemParent;
    }

    @JsonProperty("ItemParent")
    public void setItemParent(List<ItemParent> itemParent) {
        this.itemParent = itemParent;
    }

    public WSSResponse withItemParent(List<ItemParent> itemParent) {
        this.itemParent = itemParent;
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

    public WSSResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
