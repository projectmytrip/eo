
package com.eni.ioc.storedataservice.request.server;

import java.io.Serializable;
import java.util.List;

public class Element implements Serializable {

	private static final long serialVersionUID = -31171399952629836L;

    private String name;
    private String alert;
    private List<SubElement> subElements = null;

    public Element() {
    }

	public Element(String name, String alert, List<SubElement> subElements) {
		super();
		this.name = name;
		this.alert = alert;
		this.subElements = subElements;
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

	public List<SubElement> getSubElements() {
		return subElements;
	}

	public void setSubElements(List<SubElement> subElements) {
		this.subElements = subElements;
	}

	@Override
	public String toString() {
		return "Element [name=" + name + ", alert=" + alert + ", subElements=" + subElements + "]";
	}
}
