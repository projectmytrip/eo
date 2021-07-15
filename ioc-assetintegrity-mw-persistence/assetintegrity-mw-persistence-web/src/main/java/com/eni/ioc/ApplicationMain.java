package com.eni.ioc;

import static com.eni.ioc.assetintegrity.utils.CustomConfigurations.getProperty;

import java.util.Arrays;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.eni.ioc.assetintegrity.utils.SchedulerUtils;
import com.eni.ioc.assetintegrity.utils.SenderSegnaliProcesso;

@SpringBootApplication()
public class ApplicationMain{

	private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

	public static void main(String[] args){
		SpringApplication.run(ApplicationMain.class, args);
		logger.debug("-- Application Started --");
		Scheduler scheduler = SchedulerUtils.createScheduler();
        try {
            scheduler.start();
            SchedulerUtils.startSenderJob(getProperty("crontab.rest.sender"), scheduler, SenderSegnaliProcesso.class, false);
        } catch (SchedulerException ex) {
            logger.error("Error during schedulation", ex);
        }
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx){

		return args -> {
			logger.debug(String.format("Let inspect the beans provided by Spring Boot (CTX: %s)", ctx.getApplicationName()));
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames)
			{
				logger.debug(String.format("Bean name: %s", beanName));
			}
		};
	}
}
