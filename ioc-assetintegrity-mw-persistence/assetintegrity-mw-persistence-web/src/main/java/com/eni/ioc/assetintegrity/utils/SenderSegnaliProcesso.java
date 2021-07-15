package com.eni.ioc.assetintegrity.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SenderSegnaliProcesso implements Job {

	private static final Logger logger = LoggerFactory.getLogger(SenderSegnaliProcesso.class);

	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("Scheduled SenderJob service.openCriticalSignalsWaringWF(\"COVA\")");

		try {
			String result = HttpClientHelper.openCriticalSignalsWaringWF("COVA");
			logger.info("Batch result: {}", result);
		} catch (Exception e) {
			logger.error("schedule failed ", e);
		}
	}

}
