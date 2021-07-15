
package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "0", "1" })
public class ActualResponse implements Serializable {

	private static final long serialVersionUID = 2798170213063389628L;

	@JsonProperty("0")
	private HeaderBatchCall headerBatchCall;

	@JsonProperty("0")
	public HeaderBatchCall getHeaderBatchCall() {
		return headerBatchCall;
	}

	@JsonProperty("0")
	public void setHeaderBatchCall(HeaderBatchCall headerBatchCall) {
		this.headerBatchCall = headerBatchCall;
	}

	@JsonProperty("1")
	private BodyBatchCall bodyBatchCall;

	@JsonProperty("1")
	public BodyBatchCall getBodyBatchCall() {
		return bodyBatchCall;
	}

	@JsonProperty("1")
	public void setBodyBatchCall(BodyBatchCall bodyBatchCall) {
		this.bodyBatchCall = bodyBatchCall;
	}

	
	@Override
	public String toString() {
		return "ClassPojo [ headerSegnaliCritici = " + headerBatchCall.toString() + ", bodySegnaliCritici" + bodyBatchCall.toString()+"]";
	}
}
