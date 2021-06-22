package it.reply.compliance.gdpr.registry.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.registry.dto.registrytemplate.RegistryTemplateDto;
import it.reply.compliance.gdpr.registry.service.RegistryTemplateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/templates")
public class RegistryTemplateController {

    @Autowired
    private RegistryTemplateService registryTemplateService;

    @GetMapping
    public Collection<RegistryTemplateDto> getRegistryTemplate(@SortDefault(value = "id") Sort sort) {
        return registryTemplateService.getRegistryTemplates(sort);
    }

    @GetMapping("/{id}")
    public RegistryTemplateDto getRegistryTemplateById(@PathVariable Long id) {
        return registryTemplateService.getRegistryTemplateById(id);
    }

    @GetMapping(value = "/{id}/templates", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTemplateById(@PathVariable Long id) {
        return registryTemplateService.getTemplateById(id);
    }

    @GetMapping(value = "/last-template", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLastTemplate() {
        return registryTemplateService.getLastTemplate();
    }

}
