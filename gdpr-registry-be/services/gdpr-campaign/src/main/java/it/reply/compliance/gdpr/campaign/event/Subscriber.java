package it.reply.compliance.gdpr.campaign.event;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.reply.compliance.gdpr.campaign.mapper.CampaignMapper;
import it.reply.compliance.gdpr.campaign.model.Campaign;
import it.reply.compliance.gdpr.campaign.registry.RegistryClient;
import it.reply.compliance.gdpr.campaign.scheduler.SchedulerClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Subscriber {

    private final RegistryClient registryClient;
    private final SchedulerClient schedulerClient;
    private final CampaignMapper campaignMapper;

    public Subscriber(RegistryClient registryClient, SchedulerClient schedulerClient, CampaignMapper campaignMapper) {
        this.registryClient = registryClient;
        this.schedulerClient = schedulerClient;
        this.campaignMapper = campaignMapper;
    }

    //    @EventListener
    //    public void handleCampaignCreation(PayloadApplicationEvent<CampaignCreationEvent> event) {
    //        log.info("Handling campaign creation event");
    //        Campaign campaign = event.getPayload().getCampaign();
    //        registryClient.notifyCampaign(campaignMapper.detailsFromEntity(campaign));
    //    }
    //
    //    @EventListener
    //    public void handleCampaignEdit(PayloadApplicationEvent<CampaignEditEvent> event) {
    //        log.info("Handling campaign edit event");
    //        Campaign campaign = event.getPayload().getCampaign();
    //        registryClient.notifyCampaign(campaignMapper.detailsFromEntity(campaign));
    //    }

    @EventListener
    public void handleCampaignCreation(PayloadApplicationEvent<CampaignCreationEvent> event) {
        log.info("Handling campaign creation event");
        Campaign campaign = event.getPayload().getCampaign();
        schedulerClient.scheduleCampaign(campaign);
    }

    @EventListener
    public void handleCampaignEdit(PayloadApplicationEvent<CampaignEditEvent> event) {
        log.info("Handling campaign edit event");
        Campaign campaign = event.getPayload().getCampaign();
        schedulerClient.updateCampaignSchedule(campaign);
    }
}
