
package com.eni.ioc.storedataservice.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Asset",
    "ActualValue",
    "FlgIsFlaring",
    "Elements"
})
public class StoreDataServiceRequest implements Serializable
{

	private static final long serialVersionUID = 9091748976465081390L;
	
	@JsonProperty("Asset")
    private String asset;
	
    @JsonProperty("FlgIsFlaring")
    private boolean flgIsFlaring;
    
    @JsonProperty("ActualValue")
    private boolean actualValue;
    
    @JsonProperty("Elements")
    private List<Element> elements = null;
    
    public StoreDataServiceRequest() {
    }

    public StoreDataServiceRequest(String asset, boolean actualValue, List<Element> elements) {
		super();
		this.asset = asset;
		this.actualValue = actualValue;
		this.elements = elements;
	}

	@JsonProperty("Asset")
    public String getAsset() {
        return asset;
    }

    @JsonProperty("Asset")
    public void setAsset(String asset) {
        this.asset = asset;
    }

    public StoreDataServiceRequest withAsset(String asset) {
        this.asset = asset;
        return this;
    }

    @JsonProperty("FlgIsFlaring")
    public boolean isFlaring() {
        return flgIsFlaring;
    }

    @JsonProperty("FlgIsFlaring")
    public void setFlgIsFlaring(boolean flgIsFlaring) {
        this.flgIsFlaring = flgIsFlaring;
    }
    
    @JsonProperty("ActualValue")
    public boolean isActualValue() {
        return actualValue;
    }

    @JsonProperty("ActualValue")
    public void setActualValue(boolean actualValue) {
        this.actualValue = actualValue;
    }

    @JsonProperty("Elements")
    public List<Element> getElements() {
        return elements;
    }

    @JsonProperty("Elements")
    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
	
	@Override
	public String toString() {
		return "StoreDataServiceRequest [asset=" + asset + ", actualValue=" + actualValue + ", flgIsFlaring=" + flgIsFlaring + ", elements=" + elements
				+ "]";
	}
}
