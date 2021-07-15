package com.eni.ioc.scheduler;

import static com.eni.ioc.utils.EventConstants.DAY_EVENT;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.ApplicationHelper;
import com.eni.ioc.pojo.server.export.ServerExportPojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.client.StoreDataServiceServerRest;
import com.eni.ioc.storedataservice.request.server.StoreDataServiceServerRequest;

public class SenderJobServerDay implements Job {

	private static final Logger logger = LoggerFactory.getLogger(SenderJobServerDay.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		if (logger.isDebugEnabled()) {
			logger.debug("-- Job Server Day start... --");
		}

		Map<String, Map<String, ServerExportPojo>> serverDayPojo = ApplicationHelper
				.retrieveEmissionServerDayFromSource();

		StoreDataServiceServerRequest requestServerDay = StoreDataServiceServerRest
				.createDayRequest(ApplicationProperties.getEmissionSite(), DAY_EVENT, serverDayPojo, true);
		String respServerDay = StoreDataServiceServerRest
				.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(), requestServerDay);
		logger.info("Job Server Day: " + respServerDay);

	}
}
