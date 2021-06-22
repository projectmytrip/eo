package it.reply.compliance.gdpr.campaign.service;

import java.util.List;

import it.reply.compliance.gdpr.campaign.dto.CampaignDetailsDto;
import it.reply.compliance.gdpr.campaign.dto.CampaignDto;
import it.reply.compliance.gdpr.campaign.dto.CampaignRequest;

public interface CampaignService {

    List<CampaignDto> getCampaigns(CampaignRequest request);

    CampaignDetailsDto getCampaign(long campaignId);

    CampaignDetailsDto addCampaign(CampaignDetailsDto campaign);

    CampaignDto updateCampaign(long campaignId, CampaignDetailsDto updatingCampaign);
}
