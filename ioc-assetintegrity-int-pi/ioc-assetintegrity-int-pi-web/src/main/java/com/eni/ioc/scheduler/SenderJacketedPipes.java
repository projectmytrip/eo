package com.eni.ioc.scheduler;

import com.eni.ioc.pi.client.rest.PiClientRest;
import com.eni.ioc.pi.rest.response.JacketedPipesDto;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderJacketedPipes implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SenderJacketedPipes.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        logger.debug("Scheduled SenderJob PiClientRest.senderJacketedPipes()");
        List<JacketedPipesDto> data = PiClientRest.senderJacketedPipes();
        if (data != null && !data.isEmpty()) {
            StoreDataServiceRequest<JacketedPipesDto> req = new StoreDataServiceRequest();
            req.setElement(data);
            req.setAsset("COVA");
            StoreDataServiceClientRest.postStoreDataService(CustomConfigurations.getProperty("persistence.corrosion.jacketedPipes.endpoint"), req);
        } else {
            logger.warn("No new data sent.");
        }
    }

}
