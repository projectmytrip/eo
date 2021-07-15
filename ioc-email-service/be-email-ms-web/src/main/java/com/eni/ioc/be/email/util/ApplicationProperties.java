package com.eni.ioc.be.email.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.eni.ioc.be.email.util.ApplicationProperties;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {
	
	private static String MAILINGLIST_BY_EMAIL;
	
	private static ApplicationProperties singletonInstance;

	public static synchronized ApplicationProperties getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new ApplicationProperties();
		}
		return singletonInstance;
	}
	
	public String getMailinglistByEmail() {
		return MAILINGLIST_BY_EMAIL;
	}
	
	@Value("${mailinglist.service.endpoint}")
	public void setMailinglistByEmail(String mailinglist) {
		MAILINGLIST_BY_EMAIL = mailinglist;
	}

}
