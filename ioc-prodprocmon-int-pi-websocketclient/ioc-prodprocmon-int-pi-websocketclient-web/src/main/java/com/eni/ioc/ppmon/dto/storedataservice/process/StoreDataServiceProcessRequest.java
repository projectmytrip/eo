package com.eni.ioc.ppmon.dto.storedataservice.process;

import java.io.Serializable;
import java.util.List;

public class StoreDataServiceProcessRequest implements Serializable {

	private static final long serialVersionUID = 4721801996223449128L;
	
    private String asset;
    private boolean actualValue;
    private List<ElementProcess> element = null;

    public StoreDataServiceProcessRequest() {
    }

    public StoreDataServiceProcessRequest(String asset, boolean actualValue, List<ElementProcess> element) {
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

	public List<ElementProcess> getElement() {
        return element;
    }

    public void setElement(List<ElementProcess> element) {
        this.element = element;
    }

    public StoreDataServiceProcessRequest withElement(List<ElementProcess> element) {
        this.element = element;
        return this;
    }

	@Override
	public String toString() {
		return "StoreDataServiceRequest [asset=" + asset + ", actualValue=" + actualValue + ", element=" + element
				+ "]";
	}
}
