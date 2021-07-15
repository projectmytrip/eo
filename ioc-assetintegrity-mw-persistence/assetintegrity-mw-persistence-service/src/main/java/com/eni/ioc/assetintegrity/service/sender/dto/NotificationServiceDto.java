package com.eni.ioc.assetintegrity.service.sender.dto;


import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;

public class NotificationServiceDto implements Serializable {
	private static final long serialVersionUID = -8389778176260161208L;

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

	@Override
	public String toString() {
		return "Notification [subject=" + subject + ", recipients=" + recipients + ", carbonCopy=" + carbonCopy
				+ ", blindCarbonCopy=" + blindCarbonCopy + ", messageHtml=" + messageHtml + ", messageText="
				+ messageText + ", attachments=" + "]";
	}

}
