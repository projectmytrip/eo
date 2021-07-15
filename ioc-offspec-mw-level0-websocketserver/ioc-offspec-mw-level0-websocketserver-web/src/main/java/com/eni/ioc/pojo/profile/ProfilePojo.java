package com.eni.ioc.pojo.profile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uid",
    "roles",
    "domains",
    
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfilePojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -774302996269559242L;

	@JsonProperty("uid")
	private String uid;

	@JsonProperty("domains")
	private Domain[] domains;

	@JsonProperty("roles")
	private Roles[] roles;

	@JsonProperty("uid")
	public String getUid() {
		return uid;
	}

	@JsonProperty("uid")
	public void setUid(String uid) {
		this.uid = uid;
	}


	@JsonProperty("domains")
	public Domain[] getDomains() {
		return domains;
	}

	@JsonProperty("domains")
	public void setDomains(Domain[] domains) {
		this.domains = domains;
	}

	@JsonProperty("roles")
	public Roles[] getRoles() {
		return roles;
	}

	@JsonProperty("roles")
	public void setRoles(Roles[] roles) {
		this.roles = roles;
	}



	@Override
	public String toString() {
		return "ClassPojo [uid = " + uid + ", domains = " + domains + ", roles = " + roles
				+ "]";
	}
}
