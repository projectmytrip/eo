package com.eni.ioc.properties.util;

public class ApplicationProperties {

	private ApplicationProperties() {	
	}

	public static String getPersistenceUrl() {
		return EnvironmentAwareImpl.getProperty("persistence.url");
	}

	public static String getPersistenceUrlThreshold() {
		return EnvironmentAwareImpl.getProperty("persistence.url.threshold");
	}

	public static String getPiUsername() {
		return EnvironmentAwareImpl.getProperty("credential.pi.wss.username");
	}

	public static String getPiPassword() {
		return EnvironmentAwareImpl.getProperty("credential.pi.wss.password");
	}
	
	public static String getWssPIUrl() {
		return EnvironmentAwareImpl.getProperty("wss.pi.url");
	}
	
	public static long getWssBasetime() {
		return Long.parseLong(EnvironmentAwareImpl.getProperty("wss.basetime"));
	}
	
	public static int getWssMaxRetries() {
		return Integer.parseInt(EnvironmentAwareImpl.getProperty("wss.maxretries"));
	}
	
	public static int getWssMaxImmediateRetries() {
		return Integer.parseInt(EnvironmentAwareImpl.getProperty("wss.maximmediateretries"));
	}
}