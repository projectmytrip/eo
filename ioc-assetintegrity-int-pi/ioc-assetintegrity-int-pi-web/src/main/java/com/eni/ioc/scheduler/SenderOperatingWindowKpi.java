package com.eni.ioc.scheduler;

import com.eni.ioc.pi.client.rest.PiClientRest;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.OperatingWindows;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderOperatingWindowKpi implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SenderOperatingWindowKpi.class);
     
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("Scheduled SenderJob PiClientRest.getOperatingWindowsKpi().");
        List<OperatingWindows> data = PiClientRest.getOperatingWindowsKpi();
        if (data != null && !data.isEmpty()) {
            StoreDataServiceRequest<OperatingWindows> req = new StoreDataServiceRequest<>();
            req.setElement(data);
            req.setAsset("COVA");
            StoreDataServiceClientRest.postStoreDataService(CustomConfigurations.getProperty("persistence.windows.endpoint"), req);
        } else {
            logger.warn("No new data sent.");
        }
    }
    
}
