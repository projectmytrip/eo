package com.eni.ioc.ppmon.dto.storedataservice;

import java.io.Serializable;
import java.util.List;

public class StoreDataServiceRequest implements Serializable {

	private static final long serialVersionUID = 4721801996223449128L;
	
    private String asset;
    private boolean actualValue;
    private List<Element> element = null;

    public StoreDataServiceRequest() {
    }

    public StoreDataServiceRequest(String asset, boolean actualValue, List<Element> element) {
        super();
        this.asset = asset;
        this.actualValue = actualValue;
        this.element = element;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

	public boolean isActualValue() {
		return actualValue;
	}

	public void setActualValue(boolean actualValue) {
		this.actualValue = actualValue;
	}

	public List<Element> getElement() {
        return element;
    }

    public void setElement(List<Element> element) {
        this.element = element;
    }

    public StoreDataServiceRequest withElement(List<Element> element) {
        this.element = element;
        return this;
    }

	@Override
	public String toString() {
		return "StoreDataServiceRequest [asset=" + asset + ", actualValue=" + actualValue + ", element=" + element
				+ "]";
	}
}
