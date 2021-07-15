/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrosionCouponResponse {

    @JsonProperty("values")
    CorrosionCouponItem items;

    public CorrosionCouponItem getItems() {
        return items;
    }

    public void setItems(CorrosionCouponItem items) {
        this.items = items;
    }
    
}
