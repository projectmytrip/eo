package com.eni.ioc.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class So2PredictiveAverageJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(So2PredictiveAverageJob.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String resp = JobUtils.executeAverages();
		
		logger.info("So2PredictiveAverageJob response: " + resp);
	}
}
