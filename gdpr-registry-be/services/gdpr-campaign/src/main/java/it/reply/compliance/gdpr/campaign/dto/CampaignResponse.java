package it.reply.compliance.gdpr.campaign.dto;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignResponse {

    private Collection<CampaignDto> campaigns;
}
