package com.eni.ioc.scheduler;

import com.eni.ioc.pi.client.rest.PiClientRest;
import com.eni.ioc.pi.rest.response.CorrosionCouponItem;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SenderCorrosionCoupon implements Job {
    
    private static final Logger logger = LoggerFactory.getLogger(SenderCorrosionCoupon.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("Scheduled SenderJob PiClientRest.getCorrosionCoupon().");
        List<CorrosionCouponItem> data = PiClientRest.getCorrosionCoupon();
        if (data != null && !data.isEmpty()) {
            StoreDataServiceRequest<CorrosionCouponItem> req = new StoreDataServiceRequest();
            req.setElement(data);
            req.setAsset("COVA");
            StoreDataServiceClientRest.postStoreDataService(CustomConfigurations.getProperty("persistence.corrosion.coupon.endpoint"), req);
        } else {
            logger.warn("No new data sent.");
        }
    }
}