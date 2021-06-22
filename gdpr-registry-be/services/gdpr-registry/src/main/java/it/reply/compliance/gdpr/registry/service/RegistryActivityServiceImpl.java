package it.reply.compliance.gdpr.registry.service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.registry.dto.registryactivity.RegistryActivityDto;
import it.reply.compliance.gdpr.registry.mapper.RegistryActivityMapper;
import it.reply.compliance.gdpr.registry.model.RegistryActivity;
import it.reply.compliance.gdpr.registry.repository.RegistryActivityRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistryActivityServiceImpl implements RegistryActivityService {

    private final RegistryActivityRepository registryActivityRepository;
    private final RegistryActivityMapper registryActivityMapper;

    @Autowired
    public RegistryActivityServiceImpl(RegistryActivityRepository registryActivityRepository,
            RegistryActivityMapper registryActivityMapper) {
        this.registryActivityRepository = registryActivityRepository;
        this.registryActivityMapper = registryActivityMapper;
    }

    @Override
    public Collection<RegistryActivityDto> getRegistryActivityByRegistryId(Long registryId) {
        Specification<RegistryActivity> specification = SpecificationUtils.of(RegistryActivity.class)
                .and(SpecificationUtils.hasProperty("registryId", registryId));
        return registryActivityRepository.findAll(specification)
                .stream()
                .map(registryActivityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RegistryActivityDto addRegistryActivity(long registryId, RegistryActivityDto activityDto) {
        activityDto.setRegistryId(registryId);
        RegistryActivity activity = registryActivityMapper.toEntity(activityDto);
        RegistryActivity savedEntity = registryActivityRepository.save(activity);
        return registryActivityMapper.fromEntity(savedEntity);
    }

    @Override
    public RegistryActivityDto updateRegistryActivity(long registryId, long activityId,
            RegistryActivityDto activityDto) {
        RegistryActivity registryActivity = registryActivityRepository.findById(activityId)
                .orElseThrow(() -> new NoSuchElementException("Activity not found with id: " + activityId));
        RegistryActivity updatingActivity = registryActivityMapper.toEntity(activityDto);
        RegistryActivity updated = registryActivityMapper.update(registryActivity, updatingActivity);
        registryActivityRepository.save(updated);
        return registryActivityMapper.fromEntity(updated);
    }

    private void changeRegistryActivityStatus(RegistryActivity activity,
            RegistryActivity.Status newStatus) {
        log.info("Changing status of activity {} from {} to {}", activity.getId(), activity.getStatus(), newStatus);
        activity.setStatus(newStatus);
    }
}
