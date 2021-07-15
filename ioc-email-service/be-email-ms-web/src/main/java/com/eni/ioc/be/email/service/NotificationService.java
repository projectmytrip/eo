package com.eni.ioc.be.email.service;

import java.io.BufferedReader;
import java.io.File;

import com.eni.ioc.be.email.dto.NotificationBean;

public interface NotificationService
{
	void sendNotification(NotificationBean notification, File[] files);
	void sendMorningCheck(BufferedReader reader);
}
