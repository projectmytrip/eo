package com.eni.ioc.emission.dto.soredataservicerootcause;

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
    "Datetime",
    "CLAUS200",
    "CLAUS300",
    "CLAUS400",
    "TDA",
    "TDB",
    "runid"
})
public class DetailElement implements Serializable, Comparable<DetailElement>, Comparator<DetailElement>
{	
    @JsonProperty("Datetime")
    private LocalDateTime datetime;
    @JsonProperty("CLAUS200")
    private String CLAUS200;
    @JsonProperty("CLAUS300")
    private String CLAUS300;
    @JsonProperty("CLAUS400")
    private String CLAUS400;
    @JsonProperty("TDA")
    private String TDA;
    @JsonProperty("TDB")
    private String TDB;
    @JsonProperty("runid")
    private String runid;
   
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1743941975976017926L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailElement() {
    	
    } 
     
    public DetailElement(LocalDateTime datetime, String cLAUS200, String cLAUS300, String cLAUS400, String tDA,
			String tDB, String runid, Map<String, Object> additionalProperties) {

		this.datetime = datetime;
		CLAUS200 = cLAUS200;
		CLAUS300 = cLAUS300;
		CLAUS400 = cLAUS400;
		TDA = tDA;
		TDB = tDB;
		this.runid = runid;
		this.additionalProperties = additionalProperties;
	}
	
    @JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}
    
	@JsonProperty("Datetime")
    public LocalDateTime getDatetime() {
		return datetime;
	}

	@JsonProperty("Datetime")
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	@JsonProperty("CLAUS200")
	public String getCLAUS200() {
		return CLAUS200;
	}

	@JsonProperty("CLAUS200")
	public void setCLAUS200(String cLAUS200) {
		CLAUS200 = cLAUS200;
	}

	@JsonProperty("CLAUS300")
	public String getCLAUS300() {
		return CLAUS300;
	}

	@JsonProperty("CLAUS300")
	public void setCLAUS300(String cLAUS300) {
		CLAUS300 = cLAUS300;
	}

	@JsonProperty("CLAUS400")
	public String getCLAUS400() {
		return CLAUS400;
	}

	@JsonProperty("CLAUS400")
	public void setCLAUS400(String cLAUS400) {
		CLAUS400 = cLAUS400;
	}

	@JsonProperty("TDA")
	public String getTDA() {
		return TDA;
	}

	@JsonProperty("TDA")
	public void setTDA(String tDA) {
		TDA = tDA;
	}

	@JsonProperty("TDB")
	public String getTDB() {
		return TDB;
	}

	@JsonProperty("TDB")
	public void setTDB(String tDB) {
		TDB = tDB;
	}

	@JsonProperty("runid")
	public String getRunid() {
		return runid;
	}

	@JsonProperty("runid")
	public void setRunid(String runid) {
		this.runid = runid;
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
		return this.getDatetime().compareTo(detailElement.getDatetime());
	}

	@Override
	public int compare(DetailElement arg0, DetailElement arg1) {
		return arg0.getDatetime().compareTo(arg1.getDatetime());
	}

	@Override
	public String toString() {
		return "DetailElement [datetime=" + datetime + ", CLAUS200=" + CLAUS200 + ", CLAUS300=" + CLAUS300
				+ ", CLAUS400=" + CLAUS400 + ", TDA=" + TDA + ", TDB=" + TDB + ", runid=" + runid
				+ ", additionalProperties=" + additionalProperties + "]";
	}

	
}
