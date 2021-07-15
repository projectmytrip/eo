package com.eni.ioc.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class So2PredictiveImpactsJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(So2PredictiveImpactsJob.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String resp = JobUtils.executeImpact();
		
		logger.info("So2PredictiveImpactsJob response: " + resp);
	}
}
