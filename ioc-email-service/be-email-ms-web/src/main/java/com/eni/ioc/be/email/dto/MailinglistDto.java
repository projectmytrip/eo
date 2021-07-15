package com.eni.ioc.be.email.dto;

import java.io.Serializable;

public class MailinglistDto implements Serializable{
	
	private static final long serialVersionUID = 1345L;
	
	private String email;
	private String mailinglist_name;
	private String flg_exist;
	private String asset;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMailinglist_name() {
		return mailinglist_name;
	}
	public void setMailinglist_name(String mailinglist_name) {
		this.mailinglist_name = mailinglist_name;
	}
	public String getFlg_exist() {
		return flg_exist;
	}
	public void setFlg_exist(String flg_exist) {
		this.flg_exist = flg_exist;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}

}
