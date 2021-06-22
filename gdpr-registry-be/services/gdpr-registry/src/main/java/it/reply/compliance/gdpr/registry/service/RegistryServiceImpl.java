package it.reply.compliance.gdpr.registry.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.registry.dto.campaign.CampaignDetailsDto;
import it.reply.compliance.gdpr.registry.dto.registry.RegistryDto;
import it.reply.compliance.gdpr.registry.dto.registry.RegistryRequest;
import it.reply.compliance.gdpr.registry.dto.registry.legacy.RegistryLightDto;
import it.reply.compliance.gdpr.registry.exception.IllegalStatusChange;
import it.reply.compliance.gdpr.registry.mapper.RegistryMapper;
import it.reply.compliance.gdpr.registry.model.Registry;
import it.reply.compliance.gdpr.registry.model.RegistryActivity;
import it.reply.compliance.gdpr.registry.model.RegistryActivityAnswer;
import it.reply.compliance.gdpr.registry.model.RegistryStatusHistory;
import it.reply.compliance.gdpr.registry.repository.RegistryActivityAnswerRepository;
import it.reply.compliance.gdpr.registry.repository.RegistryRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class RegistryServiceImpl implements RegistryService, RegistryValidationService {

    private final RegistryRepository registryRepository;
    private final RegistryMapper registryMapper;
    private final RegistryActivityAnswerRepository activityAnswerRepository;

    @Autowired
    public RegistryServiceImpl(RegistryRepository registryRepository, RegistryMapper registryMapper,
            RegistryActivityAnswerRepository activityAnswerRepository) {
        this.registryRepository = registryRepository;
        this.registryMapper = registryMapper;
        this.activityAnswerRepository = activityAnswerRepository;
    }

    @Override
    public List<RegistryDto> getRegistries(RegistryRequest request, Sort sort) {
        Specification<Registry> specification = SpecificationUtils.of(Registry.class)
                .and(SpecificationUtils.prefetch("company"))
                .and(SpecificationUtils.prefetch("status"))
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasPropertyIn("year", request.getYears()))
                .and(SpecificationUtils.hasPropertyIn("companyId", request.getCompanies()))
                .and(SpecificationUtils.hasPropertyIn(request.getRegions(),
                        root -> root.join("company").get("regionId")))
                .and(SpecificationUtils.hasPropertyIn(request.getCountries(),
                        root -> root.join("company").get("countryId")))
                .and(SpecificationUtils.hasPropertyIn("statusId", request.getStatus()));
        return registryRepository.findAll(specification, sort)
                .stream()
                .map(registryMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RegistryDto getRegistryById(Long id) {
        Specification<Registry> specification = SpecificationUtils.of(Registry.class)
                .and(SpecificationUtils.prefetch("company"))
                .and(SpecificationUtils.prefetch("status"))
                .and(SpecificationUtils.hasProperty("id", id));
        Registry registry = registryRepository.findOne(specification)
                .orElseThrow(() -> new NoSuchElementException("Registry not found with id " + id));
        loadAnswers(registry);
        return registryMapper.fromEntity(registry);
    }

    private void loadAnswers(Registry registry) {
        Set<Long> activitiesIds = registry.getActivities()
                .stream()
                .map(RegistryActivity::getId)
                .collect(Collectors.toSet());
        Specification<RegistryActivityAnswer> specification = SpecificationUtils.of(RegistryActivityAnswer.class)
                .and(SpecificationUtils.hasPropertyIn("activityId", activitiesIds));
        Map<Long, List<RegistryActivityAnswer>> activityAnswersMap = activityAnswerRepository.findAll(specification)
                .stream()
                .collect(Collectors.groupingBy(RegistryActivityAnswer::getActivityId));
        registry.getActivities()
                .forEach(activity -> activity.setLoadedAnswers(
                        activityAnswersMap.getOrDefault(activity.getId(), Collections.emptyList())));
    }

    @Override
    public RegistryDto addRegistry(RegistryDto registryDto) {
        Registry registry = registryMapper.toEntity(registryDto);
        changeRegistryStatus(registry, Registry.Status.CREATED);
        Registry savedEntity = registryRepository.save(registry);
        return registryMapper.fromEntity(savedEntity);
    }

    @Override
    public RegistryDto updateRegistry(long registryId, RegistryDto updatingRegistryDto) {
        Registry registry = registryRepository.findById(registryId)
                .orElseThrow(() -> new NoSuchElementException("Registry not found with id " + registryId));
        Registry updatingRegistry = registryMapper.toEntity(updatingRegistryDto);
        Registry updatedRegistry = registryMapper.update(registry, updatingRegistry);
        registryRepository.save(updatedRegistry);
        return registryMapper.fromEntity(registry);
    }

    @Override
    public void campaignsNotify(CampaignDetailsDto campaignDetailsDto) {
        int currentYear = LocalDate.now().getYear();
        Specification<Registry> specification = SpecificationUtils.of(Registry.class)
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasProperty("year", currentYear))
                .and(SpecificationUtils.hasPropertyIn("companyId", campaignDetailsDto.getCompanies()))
                .and(SpecificationUtils.hasRequiredProperty("statusId", Registry.Status.OPEN));
        List<Registry> registries = registryRepository.findAll(specification);
        changeRegistryStatus(registries, Registry.Status.IN_PROGRESS);
    }

    private void changeRegistryStatus(Collection<Registry> registries, Registry.Status newStatus) {
        log.info("Changing status of {} registries", registries.size());
        registries.forEach(registry -> changeRegistryStatus(registry, newStatus));
        registryRepository.saveAll(registries);
    }

    private Registry changeRegistryStatus(Registry registry, Registry.Status newStatus) {
        Registry.Status oldStatus = registry.getStatusId();
        log.info("Changing status of registry {} from {} to {}", registry.getId(), oldStatus, newStatus);
        validateStatusChange(oldStatus, newStatus);
        registry.setStatusId(newStatus);
        changeActivitiesStatus(registry);
        createStatusHistory(registry, oldStatus, newStatus);
        return registry;
    }

    private void changeActivitiesStatus(Registry registry) {
        Registry.Status statusId = registry.getStatusId();
        registry.getActivities()
                .stream()
                .filter(RegistryActivity::isModifiable)
                .forEach(activity -> activity.setStatus(
                        registryMapper.fromRegistryStatus(statusId, activity.getStatus())));
    }

    @Override
    public List<RegistryLightDto> getRegistriesLight(RegistryRequest request, Sort sort) {
        Specification<Registry> specification = SpecificationUtils.of(Registry.class)
                .and(SpecificationUtils.prefetch("company"))
                .and(SpecificationUtils.prefetch("status"))
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasPropertyIn("year", request.getYears()))
                .and(SpecificationUtils.hasPropertyIn("companyId", request.getCompanies()))
                .and(SpecificationUtils.hasPropertyIn(request.getRegions(),
                        root -> root.join("company").get("regionId")))
                .and(SpecificationUtils.hasPropertyIn(request.getCountries(),
                        root -> root.join("company").get("countryId")))
                .and(SpecificationUtils.hasPropertyIn("statusId", request.getStatus()));
        return registryRepository.findAll(specification, sort)
                .stream()
                .map(registryMapper::lightFromEntity)
                .collect(Collectors.toList());
    }

    private Registry createStatusHistory(Registry registry, Registry.Status oldStatus, Registry.Status newStatus) {
        log.info("Creating status history of registry {} from {} to {}", registry.getId(), oldStatus, newStatus);
        RegistryStatusHistory newHistory = new RegistryStatusHistory();
        newHistory.setOldStatusId(oldStatus);
        newHistory.setNewStatusId(newStatus);
        registry.addStatusHistory(newHistory);
        return registry;
    }

    private void validateStatusChange(Registry.Status oldStatus, Registry.Status newStatus) {
        if (oldStatus == null) {
            return;
        }
        boolean allowed = Registry.ALLOWED_STATUS_CHANGES.getOrDefault(oldStatus, Collections.emptySet())
                .contains(newStatus);
        if (!allowed) {
            throw new IllegalStatusChange("Cannot change status from: " + oldStatus + " to: " + newStatus);
        }
    }

    @Override
    public void validateAsPartner(long registryId) {
        Registry registry = registryRepository.findById(registryId)
                .orElseThrow(() -> new NoSuchElementException("Registry not found with id: " + registryId));
        Registry updatedRegistry = changeRegistryStatus(registry, Registry.Status.COMPANY_VALIDATED);
        registryRepository.save(updatedRegistry);
    }

    @Override
    public void validateAsDPO(long registryId) {
        Registry registry = registryRepository.findById(registryId)
                .orElseThrow(() -> new NoSuchElementException("Registry not found with id: " + registryId));
        Registry updatedRegistry = changeRegistryStatus(registry, Registry.Status.DPO_VALIDATED);
        registryRepository.save(updatedRegistry);
    }

    @Override
    public void rejectAsDPO(long registryId) {
        Registry registry = registryRepository.findById(registryId)
                .orElseThrow(() -> new NoSuchElementException("Registry not found with id: " + registryId));
        Registry updatedRegistry = changeRegistryStatus(registry, Registry.Status.IN_PROGRESS);
        registryRepository.save(updatedRegistry);
    }
}
