package it.reply.compliance.gdpr.campaign.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import it.reply.compliance.gdpr.campaign.model.Campaign;

@Component
public class Publisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public Publisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCampaignCreation(Campaign campaign) {
        applicationEventPublisher.publishEvent(CampaignCreationEvent.builder().campaign(campaign).build());

    }

    public void publishCampaignEdit(Campaign campaign) {
        applicationEventPublisher.publishEvent(CampaignEditEvent.builder().campaign(campaign).build());
    }
}
