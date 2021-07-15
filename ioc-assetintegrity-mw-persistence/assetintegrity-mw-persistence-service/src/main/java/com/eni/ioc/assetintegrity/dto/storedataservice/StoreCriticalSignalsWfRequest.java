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
@JsonPropertyOrder({ "Asset", "TmpKey", "Wf", "approved", "closed", "start", "end", "Element", "uid" })
public class StoreCriticalSignalsWfRequest implements Serializable {

	@JsonProperty("Asset")
	private String asset;
	@JsonProperty("TmpKey")
	private String tmpKey;
	@JsonProperty("Wf")
	private String wf;
	@JsonProperty("approved")
	private boolean approved;
	@JsonProperty("closed")
	private boolean closed;
	@JsonProperty("start")
	private String start;
	@JsonProperty("end")
	private String end;
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
	public StoreCriticalSignalsWfRequest() {
	}

	public StoreCriticalSignalsWfRequest(String asset, String tmpKey, String wf, String start, String end, boolean approved, boolean closed,
			List<String> element, Map<String, Object> additionalProperties, String uid) {
		super();
		this.asset = asset;
		this.tmpKey = tmpKey;
		this.wf = wf;
		this.start = start;
		this.end = end;
		this.approved = approved;
		this.closed = closed;
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

	public StoreCriticalSignalsWfRequest withAsset(String asset) {
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

	@JsonProperty("TmpKey")
	public String getTmpKey() {
		return tmpKey;
	}

	@JsonProperty("TmpKey")
	public void setTmpKey(String tmpKey) {
		this.tmpKey = tmpKey;
	}

	@JsonProperty("Wf")
	public String getWf() {
		return wf;
	}

	@JsonProperty("Wf")
	public void setWf(String wf) {
		this.wf = wf;
	}

	@JsonProperty("approved")
	public boolean isApproved() {
		return approved;
	}

	@JsonProperty("approved")
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@JsonProperty("closed")
	public boolean isClosed() {
		return closed;
	}

	@JsonProperty("closed")
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	@JsonProperty("start")
	public String getStart() {
		return start;
	}
	
	@JsonProperty("start")
	public void setStart(String start) {
		this.start = start;
	}
	
	@JsonProperty("end")
	public String getEnd() {
		return end;
	}
	
	@JsonProperty("end")
	public void setEnd(String end) {
		this.end = end;
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

	public StoreCriticalSignalsWfRequest withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}

	@Override
	public String toString() {
		return "StoreCriticalSignalsWfRequest [asset=" + asset + ", tmpKey=" + tmpKey + ", wf=" + wf + ", approved="
				+ approved + ", closed=" + closed + ", start=" + start + ", end=" + end + ", element=" + element + "]";
	}
	
}
