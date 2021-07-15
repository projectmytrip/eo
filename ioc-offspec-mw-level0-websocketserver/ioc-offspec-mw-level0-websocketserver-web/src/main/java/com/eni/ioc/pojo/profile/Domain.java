package com.eni.ioc.pojo.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "cardName",
    "levels",
    
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Domain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7024005813339918315L;

	@JsonProperty("cardName")
	private String cardName;

	@JsonProperty("levels")
	private Level[] levels;

	@JsonProperty("code")
	private String code;

	@JsonProperty("cardName")
	public String getCardName() {
		return cardName;
	}

	@JsonProperty("cardName")
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@JsonProperty("levels")
	public Level[] getLevels() {
		return levels;
	}

	@JsonProperty("levels")
	public void setLevels(Level[] levels) {
		this.levels = levels;
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
		return "ClassPojo [cardName = " + cardName + ", levels = " + levels + ", code = " + code + "]";
	}
}
