package com.eni.ioc;

import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.request.Threshold;
import com.eni.ioc.scheduler.SchedulerUtils;
import com.eni.ioc.scheduler.SenderOffspec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.websocket.Session;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationMain {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
    public static HashMap <String,ArrayList<Threshold>> thrHashMap = new HashMap<String, ArrayList<Threshold>>();
    public static Session sessionClient = null;
        
	public static void main(String[] args) {
            SpringApplication.run(ApplicationMain.class, args);
		
            logger.debug("-- Application Started --");       
            Scheduler scheduler = SchedulerUtils.createScheduler();
            try {
                scheduler.start();
                SchedulerUtils.startSenderJob(ApplicationProperties.getOffspecCronJob(), scheduler, SenderOffspec.class, true);
            } catch (SchedulerException ex) {
                logger.error("Error during schedulation", ex);
            }
	}

	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
        	logger.info("Let inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                logger.info(beanName);
            }
        };
    }

	public static Session getSessionClient() {
		return sessionClient;
	}

	public static void setSessionClient(Session sessionClient) {
		ApplicationMain.sessionClient = sessionClient;
	}
	
	
	
	
}
