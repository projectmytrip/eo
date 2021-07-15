package com.eni.ioc.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerUtils {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);

	// NON HO TOLTO QUESTA CLASSE, CHE POTREBBE SERVIRE PER LE SCHEDULAZIONI
	
	/*
	@Autowired
	JobService jobService;
	*/


	@PostConstruct
	public void startJobConfigurationOnBoot() {
		logger.info("Started Job Configuration on boot - cron = \"0 0 0 1 * ?\"");
		//legge dalla tabella job conf e scrive sulla job value
		//cancella solo se il job è terminato
		//jobService.startJobConfiguration();
	}

	@Scheduled(cron = "0 0 0 1 * ?")
	public void startJobConfiguration() {
		logger.info("Started Job Configuration - cron = \"0 0 0 1 * ?\"");
		//legge dalla tabella job conf e scrive sulla job value
		//cancella solo se il job è terminato
		//jobService.startJobConfiguration();
	}

	@Scheduled(cron = "0 0 1 * * ?")
	//@Scheduled(fixedRate = 50000)
	public void dailyJob() {
		logger.info("Daily Job - cron = \"0 0 1 * * ?\"");
		//legge dalla tabella job value
		// 1. manda mail per inserimento valori se deadline di inserimento
		// 2. manda mail per approvazione se deadline
		// 3. manda mail per sherpa se deadline
		//jobService.startDailyJob();
	}
}
