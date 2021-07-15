package com.eni.ioc.be.email.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.be.email.dto.NotificationBean;
import com.eni.ioc.be.email.service.NotificationService;

public class RunnableUtil implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RunnableUtil.class);
	private NotificationService service;
	private NotificationBean notificationBean;
	private File[] files;

	public RunnableUtil(NotificationService service, NotificationBean notificationBean, File[] files) {
		this.service = service;
		this.notificationBean = notificationBean;
		this.files = files;
	}

	@Override
	public void run() {
		logger.info(String.format("Runnable start with notificationBean: '%s'", notificationBean));
		service.sendNotification(notificationBean, files);
	}
}
