package com.eni.ioc.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlaringAnomalyJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(FlaringAnomalyJob.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String respE13 = JobUtils.executeAnomaly("E13");
		
		logger.info("FlaringAnomalyJobE13 response: " + respE13);
		
		String respE14 = JobUtils.executeAnomaly("E14");
		
		logger.info("FlaringAnomalyJobE14 response: " + respE14);
		
		String respE15 = JobUtils.executeAnomaly("E15");
		
		logger.info("FlaringAnomalyJobE15 response: " + respE15);
	}
}
