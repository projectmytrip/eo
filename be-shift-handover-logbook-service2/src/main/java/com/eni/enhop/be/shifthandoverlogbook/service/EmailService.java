package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;
import java.util.Locale;

import com.eni.enhop.be.common.core.bean.FileDownloadBean;
import com.eni.enhop.be.email.dto.input.Notification;
import com.eni.enhop.be.shifthandoverlogbook.exception.GenericException;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;

public interface EmailService {
	void sendEmail(FileDownloadBean pdf,String emailTemplate, List<String> emailRecipients,List<String> carbonCopies, Locale locale, LogbookPage p) throws GenericException;
	void sendEMailWhitAttachment(Notification notification, FileDownloadBean attachment, String attachemntName);

}
