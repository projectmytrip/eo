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
	
	public static String getUrlCO2(){
		return EnvironmentAwareImpl.getProperty("url.co2.actual");
	}
	
	public static String getUrlDWPH2O(){
		return EnvironmentAwareImpl.getProperty("url.dwp.h2o.actual");
	}
	
	public static String getUrlDWPHC(){
		return EnvironmentAwareImpl.getProperty("url.dwp.hc.actual");
	}
	
	public static String getUrlH2SConcentration(){
		return EnvironmentAwareImpl.getProperty("url.h2s.concentration.actual");
	}
	
	public static String getUrlSulfurHRC(){
		return EnvironmentAwareImpl.getProperty("url.sulfur.hrc");
	}
	
	public static String getUrlSulfurTotal(){
		return EnvironmentAwareImpl.getProperty("url.sulfur.total");
	}
	
	public static String getUrlWobbeActual(){
		return EnvironmentAwareImpl.getProperty("url.wobbe.actual");
	}
	
	public static String getUrlCOSActual(){
		return EnvironmentAwareImpl.getProperty("url.cos.actual");
	}
        
        public static String getOffspecCronJob() {
		return EnvironmentAwareImpl.getProperty("crontab.sender.offspec");            
        }
	
}