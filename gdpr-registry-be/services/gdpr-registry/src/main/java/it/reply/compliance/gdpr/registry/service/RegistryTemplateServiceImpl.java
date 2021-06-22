package it.reply.compliance.gdpr.registry.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.registry.dto.registrytemplate.RegistryTemplateDto;
import it.reply.compliance.gdpr.registry.mapper.RegistryTemplateMapper;
import it.reply.compliance.gdpr.registry.model.RegistryTemplate;
import it.reply.compliance.gdpr.registry.repository.RegistryTemplateRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistryTemplateServiceImpl implements RegistryTemplateService {

    private final RegistryTemplateRepository registryTemplateRepository;
    private final RegistryTemplateMapper registryTemplateMapper;

    @Autowired
    public RegistryTemplateServiceImpl(RegistryTemplateRepository registryTemplateRepository, RegistryTemplateMapper registryTemplateMapper) {
        this.registryTemplateRepository = registryTemplateRepository;
        this.registryTemplateMapper = registryTemplateMapper;
    }

    public List<RegistryTemplateDto> getRegistryTemplates(Sort sort) {
        Specification<RegistryTemplate> specification = SpecificationUtils.of(RegistryTemplate.class);
        return registryTemplateRepository.findAll(specification, sort)
                .stream()
                .map(registryTemplateMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public RegistryTemplateDto getRegistryTemplateById(Long id) {
        Specification<RegistryTemplate> specification = SpecificationUtils.of(RegistryTemplate.class)
                .and(SpecificationUtils.hasProperty("id", id));
        return registryTemplateRepository.findOne(specification)
                .map(registryTemplateMapper::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Registry Template not found with id " + id));
    }

    public String getTemplateById(Long id) {
        return getRegistryTemplateById(id).getTemplate();
    }

    public RegistryTemplateDto getLastRegistryTemplate() {
        Specification<RegistryTemplate> specification = SpecificationUtils.of(RegistryTemplate.class)
                .and(SpecificationUtils.hasPropertyMaxValue(RegistryTemplate.class, Long.class, "id"));
        return registryTemplateRepository.findOne(specification)
                .map(registryTemplateMapper::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("Last Registry Template not found"));
    }

    public String getLastTemplate() {
        return getLastRegistryTemplate().getTemplate();
    }
}
