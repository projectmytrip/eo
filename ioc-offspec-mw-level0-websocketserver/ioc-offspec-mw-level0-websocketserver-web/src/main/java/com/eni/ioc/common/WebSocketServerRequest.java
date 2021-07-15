package com.eni.ioc.common;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "asset",
    "event",
    "keyname",
    "message"
   
})
public class WebSocketServerRequest implements Serializable
{


    @JsonProperty("asset")
    private String asset;
    @JsonProperty("event")
    private String event;
    @JsonProperty("keyname")
    private String keyname;
    @JsonProperty("message")
    private String message;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1743941975976017926L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WebSocketServerRequest() {
    }

   
    public WebSocketServerRequest(String asset, String event, String keyname, String message) {
        super();
        this.asset = asset;
        this.event = event;
        this.keyname = keyname;
        this.message = message;

    }


    @JsonProperty("asset")
    public String getAsset() {
        return asset;
    }

    @JsonProperty("asset")
    public void setAsset(String asset) {
        this.asset = asset;
    }

    
    @JsonProperty("event")
    public String getEvent() {
        return event;
    }

    @JsonProperty("event")
    public void setEvent(String event) {
        this.event = event;
    }
    
    
    @JsonProperty("keyname")
    public String getKeyname() {
        return keyname;
    }

    @JsonProperty("keyname")
    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }
    
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

   
}
