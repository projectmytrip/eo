package com.eni.ioc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.eni.ioc.common.DetailSession;
import com.eni.ioc.utils.JWTUtils;



@SpringBootApplication
public class ApplicationMain {

   private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
    
   public static  Map<String, ArrayList<DetailSession>> mapAssetSession = Collections.synchronizedMap(new HashMap<String, ArrayList<DetailSession>>());
    

	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class, args);
		
        logger.info("-- Application Started --");

        // calcolo della public key
        new JWTUtils();
        
        //logger.info("-- Caricata la Public Key --");

	}

	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

	
	
	
	
	
}
