package com.eni.ioc.ppmon.dto.storedataservice;

import java.io.Serializable;
import java.util.List;

public class Element implements Serializable {
	
	private static final long serialVersionUID = 2961724763822376551L;
	
    private String name;
    private List<DetailElement> detailElement = null;

    public Element() {
    }

    public Element(String name, List<DetailElement> detailElement) {
        super();
        this.name = name;
        this.detailElement = detailElement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DetailElement> getDetailElement() {
        return detailElement;
    }

    public void setDetailElement(List<DetailElement> detailElement) {
        this.detailElement = detailElement;
    }

	@Override
	public String toString() {
		return "Element [name=" + name + ", detailElement=" + detailElement + "]";
	}
}
