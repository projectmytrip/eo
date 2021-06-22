package it.reply.compliance.gdpr.registry.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.registry.dto.registrystatus.RegistryStatusDto;
import it.reply.compliance.gdpr.registry.service.RegistryStatusService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/registryStatus")
public class RegistryStatusController {

    @Autowired
    private RegistryStatusService registryStatusService;

    @GetMapping
    public Collection<RegistryStatusDto> getRegistryStatusList() {
        return registryStatusService.getRegistryStatusList();
    }

}