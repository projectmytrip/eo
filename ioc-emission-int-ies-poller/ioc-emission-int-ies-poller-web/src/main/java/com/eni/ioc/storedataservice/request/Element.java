
package com.eni.ioc.storedataservice.request;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Name",
    "Alert",
    "State",
    "Flaring",
    "SubElements",
    "from",
    "to"
})
public class Element implements Serializable {
	
	private static final long serialVersionUID = 4179029680590977698L;
	
	@JsonProperty("Name")
    private String name;
	
	@JsonProperty("Alert")
    private String alert;
	
	@JsonProperty("State")
    private String state;
	
	@JsonProperty("Flaring")
    private boolean flaring;
	
	@JsonProperty("from")
	private Timestamp from;
	
	@JsonProperty("to")
	private Timestamp to;
	
    @JsonProperty("SubElements")
    private List<SubElement> subElements = null;

    public Element() {
    }

    public Element(String name, String alert, String state, boolean flaring, List<SubElement> subElements, Timestamp from, Timestamp to) {
		super();
		this.name = name;
		this.alert = alert;
		this.state = state;
		this.flaring = flaring;
		this.subElements = subElements;
		this.from = from;
		this.to = to;
	}
    
	@JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Alert")
    public String getAlert() {
        return alert;
    }

    @JsonProperty("Alert")
    public void setAlert(String alert) {
        this.alert = alert;
    }
    
    @JsonProperty("State")
    public String getState() {
        return state;
    }

    @JsonProperty("State")
    public void setState(String state) {
        this.state = state;
    }
    
    @JsonProperty("Flaring")
    public boolean isFlaring() {
        return flaring;
    }

    @JsonProperty("Flaring")
    public void setFlaring(boolean flaring) {
        this.flaring = flaring;
    }

    
    @JsonProperty("SubElements")
    public List<SubElement> getSubElements() {
        return subElements;
    }

    @JsonProperty("SubElements")
    public void setSubElements(List<SubElement> subElements) {
        this.subElements = subElements;
    }
    
    @JsonProperty("from")
	public Timestamp getFrom() {
		return from;
	}

	@JsonProperty("from")
	public void setFrom(Timestamp from) {
		this.from = from;
	}

	@JsonProperty("to")
	public Timestamp getTo() {
		return to;
	}

	@JsonProperty("to")
	public void setTo(Timestamp to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Element [name=" + name + ", alert=" + alert + ", state=" + state + ", flaring=" + flaring + ", from="
				+ from + ", to=" + to + ", subElements=" + subElements + "]";
	}
}
