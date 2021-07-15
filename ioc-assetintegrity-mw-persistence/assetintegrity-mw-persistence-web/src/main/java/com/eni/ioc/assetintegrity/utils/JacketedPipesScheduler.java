package com.eni.ioc.assetintegrity.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacketedPipesScheduler implements Job {

	private static final Logger logger = LoggerFactory.getLogger(JacketedPipesScheduler.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("Scheduled JacketedPipesScheduler service.sendBadJackedPipesMail(\"COVA\")");

		try {
			
			// cannot autowire in quartz -_-"
			//assetIntegrityService.sendBadJackedPipesMail("COVA");
			
			HttpClientHelper.callBadJacketedPipesMailEndpoint("COVA");
			
			logger.info("Batch executed");
		} catch (Exception e) {
			logger.error("schedule failed ", e);
		}
	}

}
