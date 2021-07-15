package com.eni.ioc.assetintegrity.service.sender;

public interface MessageService {
    
    public void sendNotificationError(String messageTex, String subjectText, String recipients);
    
}
