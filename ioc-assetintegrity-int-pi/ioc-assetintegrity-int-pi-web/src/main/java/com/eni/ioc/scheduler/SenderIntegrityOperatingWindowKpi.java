package com.eni.ioc.scheduler;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.pi.client.rest.PiClientRest;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.IntegrityOperatingWindows;
import com.eni.ioc.request.OperatingWindows;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;

public class SenderIntegrityOperatingWindowKpi implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SenderIntegrityOperatingWindowKpi.class);
     
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("Scheduled SenderJob PiClientRest.getIntegrityOperatingWindowsKpi().");
        List<IntegrityOperatingWindows> data = PiClientRest.getIntegrityOperatingWindowsKpi();
        if (data != null && !data.isEmpty()) {
            StoreDataServiceRequest<IntegrityOperatingWindows> req = new StoreDataServiceRequest<>();
            req.setElement(data);
            req.setAsset("COVA");
            StoreDataServiceClientRest.postStoreDataService(CustomConfigurations.getProperty("persistence.iow.endpoint"), req);
        } else {
            logger.warn("No new data sent.");
        }
    }
    
}
