package com.eni.ioc;

import java.util.Arrays;

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

import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.scheduler.JobUtils;
import com.eni.ioc.scheduler.SchedulerUtils;



@SpringBootApplication
public class ApplicationMain {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
    
    public static String status = "DOWN"; 

	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class, args);

		JobUtils.executeProbabilities();
		JobUtils.executeImpact();
		JobUtils.executeAverages();
		JobUtils.executeAnomaly("E13");
		JobUtils.executeAnomaly("E14");
		JobUtils.executeAnomaly("E15");
		JobUtils.executeProbabilitiesFlar("E13");
		JobUtils.executeProbabilitiesFlar("E14");
		JobUtils.executeProbabilitiesFlar("E15");
		JobUtils.executeFlaringEvent("E13");
		JobUtils.executeFlaringEvent("E14");
		JobUtils.executeFlaringEvent("E15");
		JobUtils.executeRootCause();
		
		Scheduler scheduler = SchedulerUtils.createScheduler();
		try {
			scheduler.start();
	        SchedulerUtils.startRetrieveSO2PredictiveProbabilitiesJob(ApplicationProperties.getInstance().getCrontabClouderaRetriever(), scheduler);
	        SchedulerUtils.startRetrieveSO2PredictiveImpactsJob(ApplicationProperties.getInstance().getCrontabClouderaRetriever(), scheduler);
	        SchedulerUtils.startRetrieveSO2PredictiveAverageJob(ApplicationProperties.getInstance().getCrontabClouderaRetrieverAvg(), scheduler);
	        SchedulerUtils.startRetrieveFlaringAnomalyJob(ApplicationProperties.getInstance().getCrontabClouderaRetriever(), scheduler);
	        SchedulerUtils.startRetrieveFlarPredictiveProbabilitiesJob(ApplicationProperties.getInstance().getCrontabClouderaRetriever(), scheduler);
	        SchedulerUtils.startRetrieveFlaringEvents(ApplicationProperties.getInstance().getCrontabClouderaRetriever(), scheduler);
	        SchedulerUtils.startRetrieveFlaringTda(ApplicationProperties.getInstance().getCrontabClouderaRetriever(), scheduler);



		} catch (SchedulerException e) {
			logger.error("Error during schedulation", e);
		}
      

	}

	
	public static String getStatus() {
		return status;
	}


	public static void setStatus(String status) {
		ApplicationMain.status = status;
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
