package com.eni.ioc.be.email.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class NotificationBean {
	private static final long serialVersionUID = 1454023531385500118L;

	@NotEmpty
	private String subject;

	@NotEmpty
	private List<String> recipients;

	private List<String> carbonCopy;
	private List<String> blindCarbonCopy;
	
	@NotEmpty
	private String messageHtml;

	@NotEmpty
	private String messageText;

	@NotEmpty
	private boolean testedMail;
	
	@NotEmpty
	private boolean userSpecificMail;
	
	@NotEmpty
	private List<String> attachments;

	@NotEmpty
	private boolean isCalendar;

	private String senderAddress;
	private String senderName;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public List<String> getCarbonCopy() {
		return carbonCopy;
	}

	public void setCarbonCopy(List<String> carbonCopy) {
		this.carbonCopy = carbonCopy;
	}

	public List<String> getBlindCarbonCopy() {
		return blindCarbonCopy;
	}

	public void setBlindCarbonCopy(List<String> blindCarbonCopy) {
		this.blindCarbonCopy = blindCarbonCopy;
	}

	public String getMessageHtml() {
		return messageHtml;
	}

	public void setMessageHtml(String messageHtml) {
		this.messageHtml = messageHtml;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	
	public boolean isTestedMail() {
		return testedMail;
	}
	
	public void setTestedMail(boolean testedMail) {
		this.testedMail = testedMail;
	}
	
	public boolean isUserSpecificMail() {
		return userSpecificMail;
	}
	
	public void setUserSpecificMail(boolean userSpecificMail) {
		this.userSpecificMail = userSpecificMail;
	}

	public boolean isCalendar() { return isCalendar; }

	public void setCalendar(boolean isCalendar) { this.isCalendar = isCalendar; }

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@Override
	public String toString() {
		return "NotificationBean{" + "subject='" + subject + '\'' + ", recipients=" + recipients + ", carbonCopy=" +
				carbonCopy + ", blindCarbonCopy=" + blindCarbonCopy + ", messageHtml='" + messageHtml + '\'' +
				", messageText='" + messageText + '\'' + ", testedMail=" + testedMail + ", userSpecificMail=" +
				userSpecificMail + ", attachments=" + attachments + ", isCalendar=" + isCalendar + ", senderAddress" +
				"='" +
				senderAddress + '\'' + ", senderName='" + senderName + '\'' + '}';
	}
}
