package com.eni.ioc.be.email.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.eni.ioc.be.email.dto.MailinglistDto;
import com.eni.ioc.be.email.util.ProfileUtils;
import com.eni.ioc.be.email.util.RolesPojo;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailSenderProperties {

	private static final long serialVersionUID = -5063189259750840049L;

	private static final Logger logger = LoggerFactory.getLogger(EmailSenderProperties.class);

	public static final String FILE_PREFIX = "_0es0_";

	@Value("${email.from}")
	private String emailFrom;

	@Value("${email.pwd}")
	private String emailPassword;

	@Value("${email.host}")
	private String emailHost;

	@Value("${email.env}")
	private String emailEnv;

	@Value("${email.port}")
	private String emailPort;

	@Value("${email.tls.enable}")
	private String emailTlsEnable;

	@Value("${email.senderAddress}")
	private String emailSenderAddress;

	@Value("${email.senderName}")
	private String emailSenderName;

	@Value("${is.productive.env}")
	private Boolean isProductiveEnv;

	@Autowired
	ProfileUtils profileUtils;

	private static EmailSenderProperties singletonInstance;

	public EmailSenderProperties() {
	}

	public static synchronized EmailSenderProperties getInstance() {
		if(singletonInstance == null) {
			singletonInstance = new EmailSenderProperties();
		}
		return singletonInstance;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public String getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}

	public String getEmailTlsEnable() {
		return emailTlsEnable;
	}

	public void setEmailTlsEnable(String emailTlsEnable) {
		this.emailTlsEnable = emailTlsEnable;
	}

	public String getEmailSenderAddress() {
		return emailSenderAddress;
	}

	public void setEmailSenderAddress(String emailSenderAddress) {
		this.emailSenderAddress = emailSenderAddress;
	}

	public String getEmailSenderName() {
		return emailSenderName;
	}

	public void setEmailSenderName(String emailSenderName) {
		this.emailSenderName = emailSenderName;
	}

	public Boolean getIsProductiveEnv() {
		return isProductiveEnv;
	}

	public void setIsProductiveEnv(Boolean isProductiveEnv) {
		this.isProductiveEnv = isProductiveEnv;
	}

	private void sendMail(List<String> to, List<String> cc, List<String> bcc, String subject, String plain_text,
			String html_text, File[] files, boolean isCalendar, String senderAddress, String senderName) {
		logger.debug("MySelf: {}", this.toString());

		Properties props = new Properties();
		props.put("mail.smtp.host", emailHost);
		props.put("mail.smtp.port", emailPort);
		props.put("mail.smtp.socketFactory.port", emailPort);
		props.put("mail.smtp.starttls.enable", emailTlsEnable);

		Authenticator authenticator = null;

		if(!StringUtils.isBlank(emailFrom) && !StringUtils.isBlank(emailPassword)) {
			props.put("mail.smtp.auth", "true");
			authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailFrom, emailPassword);
				}
			};
		}

		Session session = Session.getInstance(props, authenticator);

		try {
			MimeMessage msg = new MimeMessage(session);

			if((senderAddress != null) && (senderAddress.length() > 0)) {
				msg.setFrom(new InternetAddress(senderName + " <" + senderAddress + ">"));
			} else if((emailSenderAddress != null) && (emailSenderAddress.length() > 0)){
				msg.setFrom(new InternetAddress(emailSenderName + " <" + emailSenderAddress + ">"));
			}

			if ((to != null) && (!to.isEmpty()))
				msg.setRecipients(RecipientType.TO, convertAddresses(to));

			if ((cc != null) && (!cc.isEmpty()))
				msg.setRecipients(RecipientType.CC, convertAddresses(cc));

			if ((bcc != null) && (!bcc.isEmpty()))
				msg.setRecipients(RecipientType.BCC, convertAddresses(bcc));

			if ((subject != null) && (subject.length() > 0))
				msg.setSubject(subject);

			msg.setSentDate(new Date());

			// https://stackoverflow.com/questions/3902455/mail-multipart-alternative-vs-multipart-mixed/23853079#23853079
			// the parent or main part if you will
			Multipart mainMultipart = new MimeMultipart("mixed");
			Multipart htmlAndTextMultipart = new MimeMultipart("alternative");

			if(Strings.isNotEmpty(plain_text)) {
				MimeBodyPart text = new MimeBodyPart();
				text.setContent(plain_text, "text/plain; charset=utf-8");
				htmlAndTextMultipart.addBodyPart(text);
			}

			if(Strings.isNotBlank(html_text)) {
				MimeBodyPart html = new MimeBodyPart();
				html.setContent(html_text, "text/html; charset=utf-8");
				htmlAndTextMultipart.addBodyPart(html);
			}
			// stuff the multipart into a bodypart and add the bodyPart to the mainMultipart
			MimeBodyPart htmlAndTextBodyPart = new MimeBodyPart();

			if(isCalendar) {

				htmlAndTextBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
				if(Strings.isNotBlank(html_text)) {

					htmlAndTextBodyPart.setContent(html_text, "text/calendar;charset=utf-8;method=REQUEST");
				} else {
					htmlAndTextBodyPart.setContent(htmlAndTextMultipart);
				}
			} else {
				htmlAndTextBodyPart.setContent(htmlAndTextMultipart);
			}

			mainMultipart.addBodyPart(htmlAndTextBodyPart);

			if(files != null) {
				for (File file : files) {

					if (file == null)
						continue;

					String fileName = file.getName();

					String[] splitted = fileName.split(EmailSenderProperties.FILE_PREFIX);
					if(splitted.length > 1) {
						if (!StringUtils.isBlank(fileName))
							fileName = splitted[splitted.length - 1];
						}

					MimeBodyPart bodyPart = new MimeBodyPart();
					FileDataSource source = new FileDataSource(file);
					bodyPart.setDataHandler(new DataHandler(source));
					bodyPart.setFileName(fileName);
					mainMultipart.addBodyPart(bodyPart);
				}
			}
			msg.setContent(mainMultipart);
			Transport.send(msg);

		} catch (MessagingException e) {
			logger.error("Error sending message!", e);
		}
	}

	private static InternetAddress[] convertAddresses(List<String> addressList) throws AddressException {
		if ((addressList == null) || (addressList.isEmpty()))
			return new InternetAddress[0];

		ArrayList<InternetAddress> toRet = new ArrayList<>();

		for (String addrStr : addressList) {
			if (addrStr.length() > 0)
				toRet.add(new InternetAddress(addrStr));
			}

		return toRet.toArray(new InternetAddress[0]);
	}

	private static String formatString(String text, HashMap<String, String> hm) {
		Iterator<String> it = hm.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			String val = hm.get(key);

			logger.debug("Key: {}", key);
			logger.debug("Val: {}", val);

			if (val != null)
				text = text.replaceAll(key, val);
			else
				text = text.replaceAll(key, "");
			}

		logger.debug("text:{}", text);
		return text;
	}

	public void sendEmailMessage(String subject, String bodyHtml, String bodyText, List<String> to, List<String> cc,
			List<String> bcc, File[] attachments, boolean readyForProd, boolean userSpecific, boolean isCalendar,
			String senderAddress, String senderName) throws Exception {

		HashMap<String, String> hmentities = new HashMap<>();

		String plainText = formatString(bodyText, hmentities);
		String htmlText = formatString(bodyHtml, hmentities);

		// log original email
		logger.info("log origin email to: {}", to);

		// If is not a productive environment OR view is not ready for production, the recipients are changed with test ones
		if((!subject.contains("[TEST] PROCESS SAFETY") && !subject.contains("[SVILUPPO] PROCESS SAFETY")) &&
				(!subject.contains("[TEST] IOC: Programma") && !subject.contains("[SVILUPPO] IOC: Programma")) &&
				(!isProductiveEnv || !readyForProd)) {

			logger.info("-- No productive environment, changed the recipients with default ones --");

			// DEFAULT MAIL FOR NO PRODUCTIVE ENVIRONMENT

			RolesPojo response = profileUtils.getMailiglistByEmail("COVA", "TEAM_TEST");
			String emailRecipientsTest = getEmailListAsString(response.getData());

			logger.info("Sending email to: {}", emailRecipientsTest);

			// new ArrayList<>(Arrays.asList(...)) supports add, addAll, etc
			List<String> noProdRecipients = emailRecipientsTest != null ? new ArrayList<>(Arrays.asList(emailRecipientsTest.split(","))) : null;                                             
			to = noProdRecipients;

			cc = null;
			bcc = null;
		}

		sendMail(to, cc, bcc, subject, plainText, htmlText, attachments, isCalendar, senderAddress, senderName);

	}
	
	
	/**
	 * Sending email to morning check teams
	 * @param subject
	 * @param bodyHtml
	 * @param bodyText
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param attachments
	 * @param readyForProd
	 * @param userSpecific
	 * @param isCalendar
	 * @param senderAddress
	 * @param senderName
	 * @throws Exception
	 */
	
	public void sendEmailMessageMorningCheck(String subject, String bodyHtml, String bodyText, List<String> to, List<String> cc,
			List<String> bcc, File[] attachments, boolean readyForProd, boolean userSpecific, boolean isCalendar,
			String senderAddress, String senderName) throws Exception {

		HashMap<String, String> hmentities = new HashMap<>();

		String plainText = formatString(bodyText, hmentities);
		String htmlText = formatString(bodyHtml, hmentities);

		// log original email
		logger.info("sendEmailMessageMorningCheck - log origin email to: {}", to);

		// If is not a productive environment OR view is not ready for production, the recipients are changed with test ones
		if((!subject.contains("[TEST] PROCESS SAFETY") && !subject.contains("[SVILUPPO] PROCESS SAFETY")) &&
				(!isProductiveEnv || !readyForProd)) {

			logger.info("-- No productive environment, changed the recipients with default ones --");

			// DEFAULT MAIL FOR NO PRODUCTIVE ENVIRONMENT

			RolesPojo response = profileUtils.getMailiglistByEmail("COVA", "TEAM_MORNING_CHECK");
			String emailRecipientsTest = getEmailListAsString(response.getData());

			logger.info("Sending email to: {}", emailRecipientsTest);

			// new ArrayList<>(Arrays.asList(...)) supports add, addAll, etc
			List<String> noProdRecipients = emailRecipientsTest != null ? new ArrayList<>(Arrays.asList(emailRecipientsTest.split(","))) : null;                                             
			to = noProdRecipients;

			cc = null;
			bcc = null;
		}

		sendMail(to, cc, bcc, subject, plainText, htmlText, attachments, isCalendar, senderAddress, senderName);

	}
	

	private String getEmailListAsString(List<MailinglistDto> data) {
		if(data == null || data.isEmpty()) {
			logger.warn("list empty, returning null");
			return null;
		}

		return data.stream()
				.filter(d -> "1".equals(d.getFlg_exist()))
				.map(MailinglistDto::getEmail)
				.collect(Collectors.joining(","));
	}

	@Override
	public String toString() {
		return "EmailSender [emailFrom=" + emailFrom + ", emailPassword=" + emailPassword + ", emailHost=" + emailHost
				+ ", emailPort=" + emailPort + ", emailTlsEnable=" + emailTlsEnable + ", emailSenderAddress="
				+ emailSenderAddress + ", emailSenderName=" + emailSenderName + "]";
	}

	public String getEmailEnv() {
		return emailEnv;
	}

	public void setEmailEnv(String emailEnv) {
		this.emailEnv = emailEnv;
	}
}
