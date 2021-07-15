package com.eni.ioc.pojo.client.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "level0",
    "level1",
    "level2",
    "level2a",
    "level2b",
    "level3"

})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientResponsePojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7534960198088263962L;


	@JsonProperty("level0")
	private JSONArray level0;
	
	@JsonProperty("level1")
	private JSONArray level1;
	
	@JsonProperty("level2")
	private JSONArray level2;
	
	@JsonProperty("level2a")
	private JSONArray level2a;
	
	@JsonProperty("level2b")
	private JSONArray level2b;
	
	@JsonProperty("level3")
	private JSONArray level3;
	
	
	


	@JsonProperty("level2a")
	public JSONArray getLevel2a() {
		return level2a;
	}

	@JsonProperty("level2a")
	public void setLevel2a(JSONArray level2a) {
		this.level2a = level2a;
	}

	@JsonProperty("level2b")
	public JSONArray getLevel2b() {
		return level2b;
	}

	@JsonProperty("level2b")
	public void setLevel2b(JSONArray level2b) {
		this.level2b = level2b;
	}

	@JsonProperty("level3")
	public JSONArray getLevel3() {
		return level3;
	}

	@JsonProperty("level3")
	public void setLevel3(JSONArray level3) {
		this.level3 = level3;
	}

	@JsonProperty("level0")
	public JSONArray getLevel0() {
		return level0;
	}

	@JsonProperty("level0")
	public void setLevel0(JSONArray level0) {
		this.level0 = level0;
	}


	@JsonProperty("level1")
	public JSONArray getLevel1() {
		return level1;
	}


	@JsonProperty("level1")
	public void setLevel1(JSONArray level1) {
		this.level1 = level1;
	}




	@Override
	public String toString() {
		return null;
	}
}
