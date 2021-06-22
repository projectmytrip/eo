package it.reply.compliance.gdpr.registry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.registry.dto.registry.RegistryDto;
import it.reply.compliance.gdpr.registry.dto.registry.RegistryRequest;
import it.reply.compliance.gdpr.registry.dto.registry.legacy.RegistryLightDto;
import it.reply.compliance.gdpr.registry.service.RegistryService;
import it.reply.compliance.gdpr.registry.service.RegistryValidationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/registries")
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @Autowired
    private RegistryValidationService registryValidationService;

    @GetMapping
    public List<RegistryLightDto> getRegistriesLight(RegistryRequest request,
            @SortDefault(value = "createdDateTime", direction = Sort.Direction.DESC) Sort sort) {
        return registryService.getRegistriesLight(request, sort);
    }

    @GetMapping("/{id}")
    public RegistryDto getRegistryById(@PathVariable Long id) {
        return registryService.getRegistryById(id);
    }

    @PostMapping
    public RegistryDto addRegistries(@RequestBody RegistryDto registry) {
        return registryService.addRegistry(registry);
    }

    @PostMapping("/{registryId}/validations/partner-validate")
    public ResultResponse validateRegistryAsPartner(@PathVariable long registryId) {
        registryValidationService.validateAsPartner(registryId);
        return ResultResponse.Ok;
    }

    @PostMapping("/{registryId}/validations/dpo-validate")
    public ResultResponse validateRegistryAsDPO(@PathVariable long registryId) {
        registryValidationService.validateAsDPO(registryId);
        return ResultResponse.Ok;
    }

    @PostMapping("/{registryId}/validations/dpo-reject")
    public ResultResponse rejectRegistryAsDPO(@PathVariable long registryId) {
        registryValidationService.rejectAsDPO(registryId);
        return ResultResponse.Ok;
    }

    
    /*@PutMapping("/{registryId}")
    public RegistryDto updateRegistry(@PathVariable long registryId, @RequestBody RegistryDto updatingRegistry) {
        return registryService.updateRegistry(registryId, updatingRegistry);
    }*/

}
