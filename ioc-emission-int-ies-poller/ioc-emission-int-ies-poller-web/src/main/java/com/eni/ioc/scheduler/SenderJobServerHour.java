package com.eni.ioc.scheduler;

import static com.eni.ioc.utils.EventConstants.HOUR_EVENT;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.ApplicationHelper;
import com.eni.ioc.pojo.server.hour.ServerHourPojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.client.StoreDataServiceServerRest;
import com.eni.ioc.storedataservice.request.server.StoreDataServiceServerRequest;

public class SenderJobServerHour implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(SenderJobServerHour.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		if (logger.isDebugEnabled()) {
			 logger.debug("-- Job Server Hour start... --");
		}

		Map<String, ServerHourPojo> serverHourPojo = ApplicationHelper.retrieveEmissionServerHourFromSource();
		StoreDataServiceServerRequest requestServerHour = StoreDataServiceServerRest.createHourRequest(ApplicationProperties.getEmissionSite(), HOUR_EVENT, serverHourPojo, true);
		String respServerHour = StoreDataServiceServerRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(),requestServerHour);
		logger.info("Job Server Hour: " + respServerHour);

	}
}
