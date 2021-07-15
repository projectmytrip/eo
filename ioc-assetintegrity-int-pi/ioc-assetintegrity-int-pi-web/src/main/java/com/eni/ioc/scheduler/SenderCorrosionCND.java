package com.eni.ioc.scheduler;

import com.eni.ioc.pi.client.rest.PiClientRest;
import com.eni.ioc.pi.rest.response.CorrosionCNDItem;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderCorrosionCND implements Job {
    
    private static final Logger logger = LoggerFactory.getLogger(SenderCorrosionCND.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("Scheduled SenderJob PiClientRest.getCorrosionCND().");
        List<CorrosionCNDItem> data = PiClientRest.getCorrosionCND();
        if (data != null && !data.isEmpty()) {
            StoreDataServiceRequest<CorrosionCNDItem> req = new StoreDataServiceRequest();
            req.setElement(data);
            req.setAsset("COVA");
            StoreDataServiceClientRest.postStoreDataService(CustomConfigurations.getProperty("persistence.corrosion.CND.endpoint"), req);
        } else {
            logger.warn("No new data sent.");
        }
    }
}
