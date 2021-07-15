package com.eni.ioc.pojo.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "name"    
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Level implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2438429341537367996L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("code")
	private String code;

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ClassPojo [name = " + name + ", code = " + code + "]";
	}
}