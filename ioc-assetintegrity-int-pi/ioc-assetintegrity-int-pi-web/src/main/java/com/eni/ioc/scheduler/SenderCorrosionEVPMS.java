package com.eni.ioc.scheduler;

import com.eni.ioc.pi.client.rest.PiClientRest;
import com.eni.ioc.pi.rest.response.EVPMSAlertDto;
import com.eni.ioc.pi.rest.response.EVPMSStationDto;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.StoreDataServiceEVPMS;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderCorrosionEVPMS implements Job {
    
    private static final Logger logger = LoggerFactory.getLogger(SenderCorrosionCoupon.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("Scheduled SenderJob PiClientRest.getEVPMS().");
        List<EVPMSStationDto> stations = PiClientRest.getEVPMSStations();
        List<EVPMSAlertDto> alerts = PiClientRest.getEVPMSAlerts();
        if ((stations != null && !stations.isEmpty()) || (alerts != null && !alerts.isEmpty())) {
            StoreDataServiceEVPMS req = new StoreDataServiceEVPMS();
            req.setAlerts(alerts);
            req.setStations(stations);
            req.setAsset("COVA");
            StoreDataServiceClientRest.postStoreDataService(CustomConfigurations.getProperty("persistence.EVPMS.endpoint"), req);
        } else {
            logger.warn("No new data sent.");
        }
    }
}