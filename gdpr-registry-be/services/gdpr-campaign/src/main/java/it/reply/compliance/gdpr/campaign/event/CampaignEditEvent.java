package it.reply.compliance.gdpr.campaign.event;

import it.reply.compliance.gdpr.campaign.model.Campaign;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignEditEvent {

    private Campaign campaign;
}
