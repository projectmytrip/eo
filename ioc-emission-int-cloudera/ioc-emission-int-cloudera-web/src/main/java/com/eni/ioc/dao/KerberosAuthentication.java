package com.eni.ioc.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.eni.ioc.properties.util.CustomConfigurations;

public class KerberosAuthentication {
	
	private static final Logger logger = LoggerFactory.getLogger(KerberosAuthentication.class);

	 // static variable instance (Singleton)
    private static UserGroupInformation instance = null; 
  
    // variable
    private static String krbCfg = getKerberosConf(); 
    private static String keytab = getKeytab();
          
    private static UserGroupInformation login() { 
    	
    	logger.info("-- Login (first time) --");
    	
    	logger.debug("krbCfg: " + krbCfg);
    	
        System.setProperty("java.security.krb5.conf", krbCfg);
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        //System.setProperty("sun.security.krb5.debug", "true");
        //System.setProperty("sun.security.jgss.debug", "true");
        
        Configuration conf = new Configuration();
        conf.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(conf);
        
        String keytab_usr = CustomConfigurations.getProperty("cloudera.keytab.user");
        logger.debug("keytab_usr: " + keytab_usr);
        UserGroupInformation ugi = null;
        
        try {
			ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(keytab_usr,keytab);
			logger.info("-- Obtained ugi --");
			
		} catch (IOException e) {
			logger.error("Unable to login user from keytab "+e);
		}
        
        return ugi;
        
    } 
    
    private static void reLogin() {
    	
    	logger.info("-- reLogin (if necessary) --");

    	try {
			//instance.checkTGTAndReloginFromKeytab();
			
			 if (UserGroupInformation.isLoginKeytabBased()) {
                UserGroupInformation.getLoginUser().reloginFromKeytab();
             } else {
                instance = login();
             }


        //System.setProperty("sun.security.krb5.debug", "true");
        //System.setProperty("sun.security.jgss.debug", "true");

		} catch (IOException e) {
			logger.error("Unable to login user from keytab "+e);
		}
    	
    }
  
    // static method to create instance of Singleton class 
    public static UserGroupInformation getInstance() { 
    	
    	logger.info("-- Get UserGroupInformation Instance --");
    	
        if (instance == null) 
            instance = login(); 
        else reLogin();
    
        return instance; 
    } 
    
    private static String getKerberosConf() {
	    
    	logger.info("-- Get Kerberos Configuration --");
    	return CustomConfigurations.getProperty("cloudera.kerberos.conf");
	   	
    }
    
    private static String getKeytab() {
	    
    	logger.info("-- Get Keytab --");
       	return CustomConfigurations.getProperty("cloudera.keytab");

    }

}
