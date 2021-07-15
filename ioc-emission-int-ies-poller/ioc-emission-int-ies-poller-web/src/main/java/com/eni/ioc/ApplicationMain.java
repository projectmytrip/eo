package com.eni.ioc;

import java.util.Arrays;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.scheduler.SchedulerUtils;

@SpringBootApplication
public class ApplicationMain {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class, args);
		
		if (logger.isDebugEnabled()) {
			 logger.debug("-- Application Started --");
		}
		
		IesWorker.doClient();
		
        Scheduler scheduler = SchedulerUtils.createScheduler();
        
        if (logger.isDebugEnabled()) {
			 logger.debug("-- Crontab for Job Emission is --" + ApplicationProperties.getJobEmissionSchedule());
		}
        SchedulerUtils.startSenderJobEmissions(ApplicationProperties.getJobEmissionSchedule(), scheduler);
        
        if (logger.isDebugEnabled()) {
			 logger.debug("-- Crontab for Job Flaring is --" + ApplicationProperties.getJobFlaringSchedule());
		}
        SchedulerUtils.startSenderJobFlaring(ApplicationProperties.getJobFlaringSchedule(), scheduler);
        
        
        IesWorker.doServerMinute();
        
        if (logger.isDebugEnabled()) {
        	logger.debug("-- Crontab for Job Server Minute is --" + ApplicationProperties.getJobFlaringSchedule());
		}
        SchedulerUtils.startSenderJobServerMinute(ApplicationProperties.getJobServerMinuteCrontab(), scheduler);
       
        
        IesWorker.doServerOther();
        if (logger.isDebugEnabled()) {
			 logger.debug("-- Crontab for Job Server Hour is --" + ApplicationProperties.getJobFlaringSchedule());
		}
       	SchedulerUtils.startSenderJobServerHour(ApplicationProperties.getJobServerHourCrontab(), scheduler);
      
       	if (logger.isDebugEnabled()) {
			 logger.debug("-- Crontab for Job Server Day is --" + ApplicationProperties.getJobFlaringSchedule());
		}
       	SchedulerUtils.startSenderJobServerDay(ApplicationProperties.getJobServerDayCrontab(), scheduler);
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
}
