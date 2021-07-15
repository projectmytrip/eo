
package com.eni.ioc.storedataservice.request.server;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SubElement implements Serializable {

	private static final long serialVersionUID = 5086577132950502436L;

	private String name;
	private String alert;
	private String unitsAbbreviation;
    private LocalDateTime date;
	private String value;
	private String validita;
	
	public SubElement() {
		super();
	}

	public SubElement(String name, String alert, String unitsAbbreviation, LocalDateTime date, String value, String validita) {
		super();
		this.name = name;
		this.alert = alert;
		this.unitsAbbreviation = unitsAbbreviation;
		this.date = date;
		this.value = value;
		this.validita = validita;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getUnitsAbbreviation() {
		return unitsAbbreviation;
	}

	public void setUnitsAbbreviation(String unitsAbbreviation) {
		this.unitsAbbreviation = unitsAbbreviation;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValidita() {
		return validita;
	}

	public void setValidita(String validita) {
		this.validita = validita;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "SubElement [name=" + name + ", alert=" + alert + ", unitsAbbreviation=" + unitsAbbreviation + ", date="
				+ date + ", value=" + value + ", validita=" + validita + "]";
	}
}
