package com.eni.ioc.emission.dto.soredataserviceanomaly;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sAlarm",
    "eAlarm",
    "Tag",
    "Description",
    "Unit",
    "From",
    "To",
    "Importance",
    "KeyName",
    "MeasureUnit"
})
public class DetailElement implements Serializable, Comparable<DetailElement>, Comparator<DetailElement>
{

    @JsonProperty("sAlarm")
    private LocalDateTime sAlarm;
    @JsonProperty("eAlarm")
    private LocalDateTime eAlarm;
    @JsonProperty("Tag")
    private String tag;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Unit")
    private String unit;
    @JsonProperty("From")
    private Float from;
    @JsonProperty("To")
    private Float to;
    @JsonProperty("Importance")
    private String importance;
    @JsonProperty("KeyName")
    private String keyName;
    @JsonProperty("MeasureUnit")
    private String measureUnit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1743941975976017926L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailElement() {
    	
    }
    
    
   
    public DetailElement(LocalDateTime sAlarm, LocalDateTime eAlarm, String tag, String description, String unit,
    		Float from, Float to, String importance, String keyName, String measureUnit, Map<String, Object> additionalProperties) {
		super();
		this.sAlarm = sAlarm;
		this.eAlarm = eAlarm;
		this.tag = tag;
		this.description = description;
		this.unit = unit;
		this.from = from;
		this.to = to;
		this.importance = importance;
		this.keyName = keyName;
		this.measureUnit = measureUnit;
		this.additionalProperties = additionalProperties;
	}
    

    @JsonProperty("sAlarm")
	public LocalDateTime getsAlarm() {
		return sAlarm;
	}

    @JsonProperty("sAlarm")
	public void setsAlarm(LocalDateTime sAlarm) {
		this.sAlarm = sAlarm;
	}

    @JsonProperty("eAlarm")
	public LocalDateTime geteAlarm() {
		return eAlarm;
	}
    
    @JsonProperty("eAlarm")
	public void seteAlarm(LocalDateTime eAlarm) {
		this.eAlarm = eAlarm;
	}

    @JsonProperty("Tag")
	public String getTag() {
		return tag;
	}

    @JsonProperty("Tag")
	public void setTag(String tag) {
		this.tag = tag;
	}

    @JsonProperty("Description")
	public String getDescription() {
		return description;
	}

    @JsonProperty("Description")
	public void setDescription(String description) {
		this.description = description;
	}

    @JsonProperty("Unit")
	public String getUnit() {
		return unit;
	}

    @JsonProperty("Unit")
	public void setUnit(String unit) {
		this.unit = unit;
	}

    @JsonProperty("From")
	public Float getFrom() {
		return from;
	}

    @JsonProperty("From")
	public void setFrom(Float from) {
		this.from = from;
	}

    @JsonProperty("To")
	public Float getTo() {
		return to;
	}

    @JsonProperty("To")
	public void setTo(Float to) {
		this.to = to;
	}

    @JsonProperty("Importance")
	public String getImportance() {
		return importance;
	}

    @JsonProperty("Importance")
	public void setImportance(String importance) {
		this.importance = importance;
	}

    @JsonProperty("KeyName")
	public String getKeyName() {
		return keyName;
	}

    @JsonProperty("KeyName")
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
    
    @JsonProperty("MeasureUnit")
	public String getMeasureUnit() {
		return measureUnit;
	}

    @JsonProperty("MeasureUnit")
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
    
    @JsonAnyGetter
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
		return (this.getsAlarm().isEqual(detailElement.getsAlarm()) && this.getUnit().equals(detailElement.getUnit()) && this.getKeyName().equals(detailElement.getKeyName()) ? 1 : 0);
	}

	@Override
	public int compare(DetailElement arg0, DetailElement arg1) {
		return (arg0.getsAlarm().isEqual(arg1.getsAlarm()) && arg0.getUnit().equals(arg1.getUnit()) && arg0.getKeyName().equals(arg1.getKeyName()) ? 1 : 0);
	}



	@Override
	public String toString() {
		return "DetailElement [sAlarm=" + sAlarm + ", eAlarm=" + eAlarm + ", tag=" + tag + ", description="
				+ description + ", unit=" + unit + ", from=" + from + ", to=" + to + ", importance=" + importance
				+ ", keyName=" + keyName + ", measureUnit=" + measureUnit + ", additionalProperties="
				+ additionalProperties + "]";
	}


	
	
	
}
