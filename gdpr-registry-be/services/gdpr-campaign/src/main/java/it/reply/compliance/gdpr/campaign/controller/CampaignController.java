package it.reply.compliance.gdpr.campaign.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.campaign.dto.CampaignDetailsDto;
import it.reply.compliance.gdpr.campaign.dto.CampaignDto;
import it.reply.compliance.gdpr.campaign.dto.CampaignRequest;
import it.reply.compliance.gdpr.campaign.dto.CampaignResponse;
import it.reply.compliance.gdpr.campaign.service.CampaignService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/campaigns")
class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping
    public CampaignResponse getCampaigns(CampaignRequest request) {
        List<CampaignDto> campaigns = campaignService.getCampaigns(request);
        return CampaignResponse.builder().campaigns(campaigns).build();
    }

    @GetMapping("/{campaignId}")
    public CampaignDetailsDto getCampaign(@PathVariable long campaignId) {
        return campaignService.getCampaign(campaignId);
    }

    @PostMapping
    public CampaignDto addCampaign(@RequestBody CampaignDetailsDto campaign) {
        return campaignService.addCampaign(campaign);
    }

    @PutMapping("/{campaignId}")
    public CampaignDto updateCampaign(@PathVariable long campaignId, @RequestBody CampaignDetailsDto updatingCampaign) {
        return campaignService.updateCampaign(campaignId, updatingCampaign);
    }
}
