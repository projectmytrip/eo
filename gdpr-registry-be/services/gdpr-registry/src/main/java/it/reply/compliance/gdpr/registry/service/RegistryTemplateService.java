package it.reply.compliance.gdpr.registry.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.registry.dto.registrytemplate.RegistryTemplateDto;

@Service
public interface RegistryTemplateService {

    public List<RegistryTemplateDto> getRegistryTemplates(Sort sort);

    public RegistryTemplateDto getRegistryTemplateById(Long id);

    public String getTemplateById(Long id);

    public RegistryTemplateDto getLastRegistryTemplate();

    public String getLastTemplate();
}
