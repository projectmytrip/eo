
package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrosionCNDResponse {
    
    @JsonProperty("values")
    CorrosionCNDItem items;

    public CorrosionCNDItem getItems() {
        return items;
    }

    public void setItems(CorrosionCNDItem items) {
        this.items = items;
    }
    
}
