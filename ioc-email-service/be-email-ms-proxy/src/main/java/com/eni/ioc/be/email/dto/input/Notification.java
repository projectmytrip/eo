package com.eni.ioc.be.email.dto.input;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.eni.ioc.be.email.dto.NotificationBean;

public class Notification implements Serializable {
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

	@NotEmpty
	private boolean testedMail;

	@NotEmpty
	private boolean userSpecificMail;

	@NotEmpty
	private boolean isCalendar;

	private String senderAddress;
	private String senderName;

	private String attachmentName;
	private byte[] attachment;

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

	public boolean isCalendar() {
		return isCalendar;
	}

	public void setCalendar(boolean isCalendar) {
		this.isCalendar = isCalendar;
	}

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

	/**
	 * Fills a service level NotificationBean from this data
	 * 
	 * @param nb
	 *            to be filled
	 */
	public void toBean(NotificationBean nb) {
		nb.setSubject(subject);
		nb.setRecipients(recipients);
		nb.setCarbonCopy(carbonCopy);
		nb.setBlindCarbonCopy(blindCarbonCopy);
		nb.setMessageHtml(messageHtml);
		nb.setMessageText(messageText);
		nb.setTestedMail(testedMail);
		nb.setUserSpecificMail(userSpecificMail);
		nb.setCalendar(isCalendar);
		nb.setSenderAddress(senderAddress);
		nb.setSenderName(senderName);
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return "Notification{" + "subject='" + subject + '\'' + ", recipients=" + recipients + ", carbonCopy="
				+ carbonCopy + ", blindCarbonCopy=" + blindCarbonCopy + ", messageHtml='" + messageHtml + '\''
				+ ", messageText='" + messageText + '\'' + ", testedMail=" + testedMail + ", userSpecificMail="
				+ userSpecificMail + ", isCalendar=" + isCalendar + ", senderAddress='" + senderAddress + '\''
				+ ", senderName='" + senderName + '\'' + '}';
	}
}
