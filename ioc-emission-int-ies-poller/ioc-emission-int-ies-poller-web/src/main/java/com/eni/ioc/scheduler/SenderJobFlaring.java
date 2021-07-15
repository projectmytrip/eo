package com.eni.ioc.scheduler;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.ApplicationHelper;
import com.eni.ioc.bean.RequestElementsBean;
import com.eni.ioc.pojo.flaring.FlaringPojo;
import com.eni.ioc.pojo.thresholds.ThresholdsPojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import com.eni.ioc.storedataservice.request.StoreDataServiceRequest;

public class SenderJobFlaring implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(SenderJobFlaring.class);
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		if (logger.isDebugEnabled()) {
			 logger.debug("-- Job Flaring start... --");
		}
		
		FlaringPojo flaringPojo = ApplicationHelper.retrieveFlaringFromSource();
		List<ThresholdsPojo> thresholdsPojo = ApplicationHelper.retrieveThresholdsFromSource();
		
		RequestElementsBean requestElementsBean = new RequestElementsBean();
		requestElementsBean.setFlaring(flaringPojo);
		requestElementsBean.setThresholds(thresholdsPojo);
		StoreDataServiceRequest request = StoreDataServiceClientRest.createRequest(ApplicationProperties.getEmissionSite(), requestElementsBean, true, true);
		String resp = StoreDataServiceClientRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceEndpoint(),request);
		logger.info("Job Flaring: " + resp);

	}
}
