package com.eni.ioc.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlaringEventJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(FlaringEventJob.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String respE13 = JobUtils.executeFlaringEvent("E13");
		
		logger.info("FlaringEventJobE13 response: " + respE13);
		
		String respE14 = JobUtils.executeFlaringEvent("E14");
		
		logger.info("FlaringEventJobE14 response: " + respE14);
		
		String respE15 = JobUtils.executeFlaringEvent("E15");
		
		logger.info("FlaringEventJobE15 response: " + respE15);
	}
}
