package com.eni.ioc.assetintegrity.properties.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

	private static String openCriticalSignalsWaringWFEndpoint;
	private static String badJacketedPipesMailEndpoint;

	private static ApplicationProperties singletonInstance;

	public static synchronized ApplicationProperties getInstance() {
		if ( singletonInstance == null ) {
			singletonInstance = new ApplicationProperties();
		}

		return singletonInstance;
	}

	public String getOpenCriticalSignalsWaringWFEndpoint() {
		return openCriticalSignalsWaringWFEndpoint;
	}

	@Value("${ai.opencswarningwf.service.endpoint}")
	public void setOpenCriticalSignalsWaringWFEndpoint(String openCriticalSignalsWaringWFEndpoint) {
		ApplicationProperties.openCriticalSignalsWaringWFEndpoint = openCriticalSignalsWaringWFEndpoint;
	}

	public String getBadJacketedPipesMailEndpoint() {
		return badJacketedPipesMailEndpoint;
	}

	@Value("${jacketed.pipes.mail.service.endpoint}")
	public void setBadJacketedPipesMailEndpoint(String badJacketedPipesMailEndpoint) {
		ApplicationProperties.badJacketedPipesMailEndpoint = badJacketedPipesMailEndpoint;
	}
}