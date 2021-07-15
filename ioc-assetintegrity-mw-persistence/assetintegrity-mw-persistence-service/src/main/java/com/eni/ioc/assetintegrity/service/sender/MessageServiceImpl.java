package com.eni.ioc.assetintegrity.service.sender;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.eni.ioc.assetintegrity.service.sender.dto.NotificationServiceDto;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

  
    @Autowired
    Sender sender;

    @Autowired
    private Environment env;
        
    @Override
    public void sendNotificationError(String messageText, String subjectText, String recipients) {
                
        NotificationServiceDto notificationServiceDto = new NotificationServiceDto();
        notificationServiceDto.setMessageText(messageText);
        notificationServiceDto.setRecipients(Arrays.asList(recipients.split(",")));
        String subject = env.getProperty("email.object.tag");
        if (subject == null) {
            subject = "";
        } else if (!"".equals(subject)) {
            subject += " ";
        }
        notificationServiceDto.setSubject(subject + subjectText);
        notificationServiceDto.setCarbonCopy(new ArrayList<>());
        notificationServiceDto.setMessageHtml("<p>" + messageText + "</p>");
        try {
            logger.info("Sending AM email for subject " + notificationServiceDto.getSubject());
            sender.sendToRabbitMQ(notificationServiceDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
