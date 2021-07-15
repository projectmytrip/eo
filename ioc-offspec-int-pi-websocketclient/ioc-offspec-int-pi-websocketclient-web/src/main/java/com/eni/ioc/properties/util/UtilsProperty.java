package com.eni.ioc.properties.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@Configuration
//@PropertySource("classpath:application.properties")
public class UtilsProperty { 
	
	
//    @Value("${crontab.websocketclient.sender}")
    private  String crontabWSC;

    private static UtilsProperty singletonInstance;   
    
    
    public static synchronized UtilsProperty getInstance(){
        if(singletonInstance == null){
            singletonInstance = new UtilsProperty();
        }
        return singletonInstance;
    }
	public String getCrontabWSC() {
		return crontabWSC;
	}
	public void setCrontabWSC(String crontabWSC) {
		this.crontabWSC = crontabWSC;
	}
    
    
    
    
    
    
    
    
    
}