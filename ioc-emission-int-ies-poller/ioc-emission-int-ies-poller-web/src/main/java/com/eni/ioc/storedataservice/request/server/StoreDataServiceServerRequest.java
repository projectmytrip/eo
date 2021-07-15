
package com.eni.ioc.storedataservice.request.server;

import java.io.Serializable;
import java.util.List;

public class StoreDataServiceServerRequest implements Serializable {

	private static final long serialVersionUID = -6836975337203892296L;

    private String asset;
    private boolean actualValue;
    private String event;
    private List<Element> elements = null;
    
    public StoreDataServiceServerRequest() {
    }

    public StoreDataServiceServerRequest(String asset, boolean actualValue, String event, List<Element> elements) {
		super();
		this.asset = asset;
		this.actualValue = actualValue;
		this.event = event;
		this.elements = elements;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return "StoreDataServiceServerRequest [asset=" + asset + ", actualValue=" + actualValue + ", event=" + event
				+ ", elements=" + elements + "]";
	}
}
