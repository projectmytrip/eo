package com.eni.ioc.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlarPredictiveProbabilitiesJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(FlarPredictiveProbabilitiesJob.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String respE13 = JobUtils.executeProbabilitiesFlar("E13");
		
		logger.info("FlarPredictiveProbabilitiesJobE13 response: " + respE13);
		
		String respE14 = JobUtils.executeProbabilitiesFlar("E14");
		
		logger.info("FlarPredictiveProbabilitiesJobE14 response: " + respE14);
		
		String respE15 = JobUtils.executeProbabilitiesFlar("E15");
		
		logger.info("FlarPredictiveProbabilitiesJobE15 response: " + respE15);
	}
}
