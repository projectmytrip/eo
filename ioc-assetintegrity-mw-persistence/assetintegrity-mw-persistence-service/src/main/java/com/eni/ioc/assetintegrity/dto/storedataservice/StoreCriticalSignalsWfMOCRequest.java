package com.eni.ioc.assetintegrity.dto.storedataservice;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Asset", "Wf", "moc", "Element", "uid" })
public class StoreCriticalSignalsWfMOCRequest implements Serializable {

	@JsonProperty("Asset")
	private String asset;
	@JsonProperty("Wf")
	private String wf;
	@JsonProperty("moc")
	private int moc;
	@JsonProperty("uid")
	private String uid;
	@JsonProperty("Element")
	private List<String> element = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = 4620379510248555928L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public StoreCriticalSignalsWfMOCRequest() {
	}

	public StoreCriticalSignalsWfMOCRequest(String asset, String wf, int moc,
			List<String> element, Map<String, Object> additionalProperties, String uid) {
		super();
		this.asset = asset;
		this.wf = wf;
		this.moc = moc;
		this.element = element;
		this.uid = uid;
		this.additionalProperties = additionalProperties;
	}

	@JsonProperty("Asset")
	public String getAsset() {
		return asset;
	}

	@JsonProperty("Asset")
	public void setAsset(String asset) {
		this.asset = asset;
	}

	public StoreCriticalSignalsWfMOCRequest withAsset(String asset) {
		this.asset = asset;
		return this;
	}

	@JsonProperty("Element")
	public List<String> getElement() {
		return element;
	}

	@JsonProperty("Element")
	public void setElement(List<String> element) {
		this.element = element;
	}

	@JsonProperty("moc")
	public int getMoc() {
		return moc;
	}

	@JsonProperty("moc")
	public void setMoc(int moc) {
		this.moc = moc;
	}

	@JsonProperty("Wf")
	public String getWf() {
		return wf;
	}

	@JsonProperty("Wf")
	public void setWf(String wf) {
		this.wf = wf;
	}

	@JsonProperty("uid")
	public String getUid() {
		return uid;
	}

	@JsonProperty("uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public StoreCriticalSignalsWfMOCRequest withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}

	@Override
	public String toString() {
		return "StoreCriticalSignalsWfRequest [asset=" + asset + ", moc=" + moc + ", wf=" + wf + ", element=" + element + "]";
	}

}
