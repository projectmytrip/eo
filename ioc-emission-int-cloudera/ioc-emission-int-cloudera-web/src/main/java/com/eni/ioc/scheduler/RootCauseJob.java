package com.eni.ioc.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootCauseJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(RootCauseJob.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String resp = JobUtils.executeRootCause();
		
		logger.info("RootCauseJob response: " + resp);
	}
}
