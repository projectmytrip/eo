package it.reply.compliance.gdpr.registry.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.registry.dto.registrystatus.RegistryStatusDto;
import it.reply.compliance.gdpr.registry.mapper.RegistryStatusMapper;
import it.reply.compliance.gdpr.registry.model.RegistryStatus;
import it.reply.compliance.gdpr.registry.repository.RegistryStatusRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistryStatusServiceImpl implements RegistryStatusService {

    private final RegistryStatusRepository registryStatusRepository;
    private final RegistryStatusMapper registryStatusMapper;

    @Autowired
    public RegistryStatusServiceImpl(RegistryStatusRepository registryStatusRepository,
            RegistryStatusMapper registryStatusMapper) {
        this.registryStatusRepository = registryStatusRepository;
        this.registryStatusMapper = registryStatusMapper;
    }

    @Override
    public Collection<RegistryStatusDto> getRegistryStatusList() {
        Specification<RegistryStatus> specification = SpecificationUtils.of(RegistryStatus.class);
        return registryStatusRepository.findAll(specification)
                .stream()
                .map(this.registryStatusMapper::fromEntity)
                .collect(Collectors.toList());
    }
}