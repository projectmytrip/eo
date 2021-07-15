package com.eni.ioc.ppmon.dto.storedataservice.process;

import java.io.Serializable;
import java.util.List;

public class ElementProcess implements Serializable {
	
	private static final long serialVersionUID = 2961724763822376551L;
	
    private String name;
    private List<DetailElementProcess> detailElement = null;

    public ElementProcess() {
    }

    public ElementProcess(String name, List<DetailElementProcess> detailElement) {
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

    public List<DetailElementProcess> getDetailElement() {
        return detailElement;
    }

    public void setDetailElement(List<DetailElementProcess> detailElement) {
        this.detailElement = detailElement;
    }

	@Override
	public String toString() {
		return "Element [name=" + name + ", detailElement=" + detailElement + "]";
	}
}
