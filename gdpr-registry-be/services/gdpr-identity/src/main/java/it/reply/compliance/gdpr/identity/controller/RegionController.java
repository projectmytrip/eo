package it.reply.compliance.gdpr.identity.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.identity.dto.region.RegionDto;
import it.reply.compliance.gdpr.identity.dto.region.RegionResponse;
import it.reply.compliance.gdpr.identity.service.RegionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/regions")
class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public RegionResponse getRegions() {
        Collection<RegionDto> regions = this.regionService.getRegions();
        return RegionResponse.builder().regions(regions).build();
    }
}
