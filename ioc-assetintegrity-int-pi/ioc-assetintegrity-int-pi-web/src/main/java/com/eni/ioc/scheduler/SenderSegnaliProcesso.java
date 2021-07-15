package com.eni.ioc.scheduler;

import com.eni.ioc.pi.client.rest.PiClientRest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderSegnaliProcesso implements Job {

	private static final Logger logger = LoggerFactory.getLogger(SenderSegnaliProcesso.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("Scheduled SenderJob PiClientRest.senderSegnaliProcesso()");

		PiClientRest.senderSegnaliProcesso();
	}

}
