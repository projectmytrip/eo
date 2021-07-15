package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"notification",
		"codeTemplate",
		"asset",
		"values",
		"uids",
		"group",
		"senderAddress",
		"senderName",
	    "attachmentName",
		"attachment" })
public class NotificationInput implements Serializable {

	private static final long serialVersionUID = 13414124141L;

	@JsonProperty("notification")
	private NotificationDto notification;
	@JsonProperty("codeTemplate")
	private String codeTemplate;
	@JsonProperty("asset")
	private String asset;
	@JsonProperty("values")
	private Map<String, String> values;
	@JsonProperty("uids")
	private List<UserMailDto> uids;
	@JsonProperty("group")
	private boolean group;
	@JsonProperty("senderAddress")
	private String senderAddress;
	@JsonProperty("senderName")
	private String senderName;
	@JsonProperty("attachmentName")
	private String attachmentName;
	@JsonProperty("attachment")
	private byte[] attachment;

	public NotificationInput() {

	}

	public NotificationInput(NotificationDto notification, String codeTemplate, String asset,
			Map<String, String> values, List<UserMailDto> uids) {
		super();
		this.notification = notification;
		this.uids = uids;
		this.asset = asset;
		this.codeTemplate = codeTemplate;
		this.values = values;
	}

	@JsonProperty("notification")
	public NotificationDto getNotification() {
		return notification;
	}

	@JsonProperty("notification")
	public void setNotification(NotificationDto notification) {
		this.notification = notification;
	}

	@JsonProperty("asset")
	public String getAsset() {
		return asset;
	}

	@JsonProperty("asset")
	public void setAsset(String asset) {
		this.asset = asset;
	}

	@JsonProperty("uids")
	public List<UserMailDto> getUids() {
		return uids;
	}

	@JsonProperty("uids")
	public void setUids(List<UserMailDto> uids) {
		this.uids = uids;
	}

	@JsonProperty("codeTemplate")
	public String getCodeTemplate() {
		return codeTemplate;
	}

	@JsonProperty("codeTemplate")
	public void setCodeTemplate(String codeTemplate) {
		this.codeTemplate = codeTemplate;
	}

	@JsonProperty("values")
	public Map<String, String> getValues() {
		return values;
	}

	@JsonProperty("values")
	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	@JsonProperty("group")
	public boolean getGroup() {
		return group;
	}

	@JsonProperty("group")
	public void setGroup(boolean group) {
		this.group = group;
	}

	@JsonProperty("senderAddress")
	public String getSenderAddress() {
		return senderAddress;
	}

	@JsonProperty("senderAddress")
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	@JsonProperty("senderName")
	public String getSenderName() {
		return senderName;
	}

	@JsonProperty("senderName")
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}	
	@JsonProperty("attachmentName")
	public String getAttachmentName() {
		return attachmentName;
	}
	
	@JsonProperty("attachmentName")
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	@JsonProperty("attachment")
	public byte[] getAttachment() {
		return attachment;
	}
	
	@JsonProperty("attachment")
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
}
