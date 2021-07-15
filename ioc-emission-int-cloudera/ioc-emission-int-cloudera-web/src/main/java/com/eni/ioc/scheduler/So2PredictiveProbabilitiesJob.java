package com.eni.ioc.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class So2PredictiveProbabilitiesJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(So2PredictiveProbabilitiesJob.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String resp = JobUtils.executeProbabilities();
		
		logger.info("So2PredictiveProbabilitiesJob response: " + resp);
	}
}
