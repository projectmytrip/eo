package com.eni.ioc.emission.dto.soredataserviceflaringevent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "avgEmission",
    "startEvent",
	"endEvent",
	"eventLength",
	"maxEmission",
	"totalEmission",
	"keyname"
})
public class DetailElement implements Serializable, Comparable<DetailElement>, Comparator<DetailElement>
{

    @JsonProperty("id")
	private String id;
    @JsonProperty("avgEmission")
	private Float avgEmission;
    @JsonProperty("startEvent")
	private LocalDateTime startEvent;
    @JsonProperty("endEvent")
	private LocalDateTime endEvent;
    @JsonProperty("eventLength")
	private Integer eventLength;
    @JsonProperty("maxEmission")
	private Float maxEmission;
    @JsonProperty("totalEmission")
	private Float totalEmission;	
    @JsonProperty("keyname")
	private String keyname;
  
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1743941975976017926L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailElement() {
    	
    }
   
    public DetailElement(String id, Float avgEmission, LocalDateTime startEvent, LocalDateTime endEvent, Integer eventLength,
			Float maxEmission, Float totalEmission, String keyname, Map<String, Object> additionalProperties) {
		super();
		this.id = id;
		this.avgEmission = avgEmission;
		this.startEvent = startEvent;
		this.endEvent = endEvent;
		this.eventLength = eventLength;
		this.maxEmission = maxEmission;
		this.totalEmission = totalEmission;
		this.keyname = keyname;
		this.additionalProperties = additionalProperties;
	}    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getAvgEmission() {
		return avgEmission;
	}
	public void setAvgEmission(Float avgEmission) {
		this.avgEmission = avgEmission;
	}
	
	public LocalDateTime getStartEvent() {
		return startEvent;
	}	
	
	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public void setStartEvent(LocalDateTime startEvent) {
		this.startEvent = startEvent;
	}
	
	public LocalDateTime getEndEvent() {
		return endEvent;
	}

	public void setEndEvent(LocalDateTime endEvent) {
		this.endEvent = endEvent;
	}

	public Integer getEventLength() {
		return eventLength;
	}

	public void setEventLength(Integer eventLength) {
		this.eventLength = eventLength;
	}

	public Float getMaxEmission() {
		return maxEmission;
	}

	public void setMaxEmission(Float maxEmission) {
		this.maxEmission = maxEmission;
	}

	public Float getTotalEmission() {
		return totalEmission;
	}

	public void setTotalEmission(Float totalEmission) {
		this.totalEmission = totalEmission;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	@JsonAnyGetter
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public DetailElement withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
	
	@Override
	public int compareTo(DetailElement detailElement) {
		return (this.getStartEvent().isEqual(detailElement.getStartEvent()) && this.getId().equals(detailElement.getId()) ? 1 : 0);
	}

	@Override
	public int compare(DetailElement arg0, DetailElement arg1) {
		return (arg0.getStartEvent().isEqual(arg1.getStartEvent()) && arg0.getId().equals(arg1.getId()) ? 1 : 0);
	}
}
