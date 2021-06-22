package it.reply.compliance.gdpr.registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.registry.dto.campaign.CampaignDetailsDto;
import it.reply.compliance.gdpr.registry.service.RegistryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/internal")
public class InternalController {

    @Autowired
    private RegistryService registryService;

    @PostMapping("/campaigns/notify")
    public ResponseEntity<?> campaignsNotify(@RequestBody CampaignDetailsDto campaignDetailsDto) {
        registryService.campaignsNotify(campaignDetailsDto);
        return ResponseEntity.ok(ResultResponse.Ok);
    }
}
