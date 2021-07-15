package com.eni.ioc.scheduler;

import static com.eni.ioc.utils.EventConstants.MINUTE_EVENT;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.ApplicationHelper;
import com.eni.ioc.pojo.server.minute.ServerMinutePojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.client.StoreDataServiceServerRest;
import com.eni.ioc.storedataservice.request.server.StoreDataServiceServerRequest;

public class SenderJobServerMinute implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(SenderJobServerMinute.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		if (logger.isDebugEnabled()) {
			 logger.debug("-- Job Server Minute start... --");
		}

		Map<String, ServerMinutePojo> serverMinutePojo = ApplicationHelper.retrieveEmissionServerMinuteFromSource();
		StoreDataServiceServerRequest requestServerMinute = StoreDataServiceServerRest.createMinuteRequest(ApplicationProperties.getEmissionSite(), MINUTE_EVENT, serverMinutePojo, true);
		String respServerMinute = StoreDataServiceServerRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(),requestServerMinute);
		logger.info("Job Server Minute: " + respServerMinute);
	}
}
