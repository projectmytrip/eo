package com.eni.ioc.assetintegrity.utils;

import static com.eni.ioc.assetintegrity.utils.CustomConfigurations.getProperty;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderSegnaliProcesso implements Job {

	private static final Logger logger = LoggerFactory.getLogger(SenderSegnaliProcesso.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("Scheduled SenderJob service.openCriticalSignalsWaringWF(\"COVA\")");

		try {
			HttpClientHelper.callScheduledServiceGet("COVA", getProperty("ai.opencswarningwf.service.endpoint"));
		} catch (Exception e) {
			logger.error("schedule failed ", e);
		}
	}

}
