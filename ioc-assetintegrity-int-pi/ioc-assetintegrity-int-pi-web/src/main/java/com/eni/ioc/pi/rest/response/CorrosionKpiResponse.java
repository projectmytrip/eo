
package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Items" })
public class CorrosionKpiResponse {
    
    @JsonProperty("Items")
    CorrosionKpiItem[] items;

    @JsonProperty("Items")
    public CorrosionKpiItem[] getItems() {
        return items;
    }
    
    @JsonProperty("Items")
    public void setItems(CorrosionKpiItem[] items) {
        this.items = items;
    }
  
}
