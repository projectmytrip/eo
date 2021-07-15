package com.eni.ioc.be.email.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.ioc.be.email.config.EmailSenderProperties;
import com.eni.ioc.be.email.dto.NotificationBean;
import com.eni.ioc.be.email.util.HtmlFormatter;

@Service
public class NotificationServiceImpl implements NotificationService {
	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	public EmailSenderProperties sender;

	@Override
	public void sendNotification(NotificationBean notification, File[] files) {

		try {
			logger.debug("Sending Notification {}", notification);
			String subject = notification.getSubject();
			String bodyHtml = notification.getMessageHtml();
			String bodyText = notification.getMessageText();
			sender.sendEmailMessage(subject, bodyHtml, bodyText, notification.getRecipients(),
					notification.getCarbonCopy(), notification.getBlindCarbonCopy(), files, 
					notification.isTestedMail(), notification.isUserSpecificMail(), notification.isCalendar(),
					notification.getSenderAddress(), notification.getSenderName());
			logger.debug("Notification sended");
		} catch (Exception e) {
			logger.error("Error sending email: " + e.getMessage(), e);
		} finally {

			if (files != null) {
				for (File file : files) {
					try {
						file.delete();
					} catch (SecurityException e) {
						logger.warn("Error during deleting a file", e);
					}
				}
			}
		}
	}
	
	
	@Override
	public void sendMorningCheck(BufferedReader reader) {
		
		List<String> lines = new ArrayList<>();
		String env = sender.getEmailEnv();
		
		try {
			while (reader.ready()) {
				String line = reader.readLine();
				lines.add(line);
				if(line.contains("pr-ioc")) {
					env = "[PROD]";
				}
			}
			
			
			
			List<String> firstTable = lines.subList(0, lines.indexOf("--STARTBACKUP--"));
			List<String> fourthTable = lines.subList(lines.indexOf("--STARTBACKUP--") + 1, lines.indexOf("#"));
			List<String> secondTable = lines.subList(lines.indexOf("#") + 1, lines.size());
			
			List<List<String>> firstTableHeaders = new ArrayList<>();
			List<String> firstTHeader1 = new ArrayList<>();
			firstTHeader1.add("POD STATUS (§pods_num§/82)");
			firstTableHeaders.add(firstTHeader1);
			List<String> firstTHeader2 = new ArrayList<>();
			firstTHeader2.add("NAME");
			firstTHeader2.add("READY");
			firstTHeader2.add("STATUS");
			firstTHeader2.add("RESTARTS");
			firstTHeader2.add("AGE");
			firstTHeader2.add("ESITO");
			firstTableHeaders.add(firstTHeader2);
			
			List<String> firstTableValuesToReplace = new ArrayList<>();
			firstTableValuesToReplace.add(firstTable.size()+"");
			
			List<List<String>> secondTableHeaders = new ArrayList<>();
			List<String> secondTHeader1 = new ArrayList<>();
			secondTHeader1.add("BATCH CLEAN STATUS");
			secondTableHeaders.add(secondTHeader1);
			List<String> secondTHeader2 = new ArrayList<>();
			secondTHeader2.add("BATCH CLEAN");
			secondTHeader2.add("DETAILS");
			secondTHeader2.add("CLEAN DATE");
			secondTHeader2.add("ESITO");
			secondTableHeaders.add(secondTHeader2);
			
			
			List<List<String>> thirdTableHeaders = new ArrayList<>();
			List<String> thirdTHeader1 = new ArrayList<>();
			thirdTHeader1.add("STATO NAVIGAZIONE FRONT END");
			thirdTableHeaders.add(thirdTHeader1);
			List<String> thirdTHeader2 = new ArrayList<>();
			thirdTHeader2.add("DETAILS");
			thirdTHeader2.add("ESITO");
			thirdTableHeaders.add(thirdTHeader2);
			List<String> thirdTable = new ArrayList<>();
			thirdTable.add("Navigazione Front End ioc.eni.com, OK ");

			
			List<List<String>> fourthTableHeaders = new ArrayList<>();
			List<String> fourthTHeader1 = new ArrayList<>();
			fourthTHeader1.add("BACKUP DB MYSQL");
			fourthTableHeaders.add(fourthTHeader1);
			List<String> fourthTHeader2 = new ArrayList<>();
			fourthTHeader2.add("BACKUP");
			fourthTHeader2.add("ESITO");
			fourthTableHeaders.add(fourthTHeader2);

			
			
// String title, String subtitle, List<ArrayList<String>> headers, List<String> rows, int colNums, boolean includeNumRows, List<String> valuesToReplace			
			
			String firstTableSubtitle = "Buongiorno,<br>di seguito il recap del morning check sullo IOC.";
			String table1 = HtmlFormatter.createTable(null, firstTableSubtitle, firstTableHeaders, firstTable, 6, true, firstTableValuesToReplace);
			String table2 = HtmlFormatter.createTable(null, "Batch<br>", secondTableHeaders, secondTable, 4, false, null);
			String table3 = HtmlFormatter.createTable(null, "Front End<br>", thirdTableHeaders, thirdTable, 2, false, null);
			String table4 = HtmlFormatter.createTable(null, "Backup DB<br>", fourthTableHeaders, fourthTable, 2, false, null);

			StringBuilder bodyHtml = new StringBuilder();
			bodyHtml.append("<style>tr,th,td{ border:1px solid black; padding: 3px; }</style>");
			bodyHtml.append(table1).append(table2).append(table4).append(table3);
			
			
			logger.debug("Sending Morning Check ");
			
			logger.info(bodyHtml.toString());
			
			String subject = env+" Morning Check";

			try {
				//sender.sendEmailMessage(subject, bodyHtml.toString(), null, null,null, null, null, true, false, false, null, null);
				sender.sendEmailMessageMorningCheck(subject, bodyHtml.toString(), null, null,null, null, null, true, false, false, null, null);
				
			} catch (Exception e) {
				logger.error("",e);
			}
			logger.debug("Morning check sended");
			
		} catch (IOException e) {
			logger.error("",e);
		}
				
		logger.info("FINE FILE");
	}


}
