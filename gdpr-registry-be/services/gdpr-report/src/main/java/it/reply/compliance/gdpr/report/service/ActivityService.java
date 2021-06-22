package it.reply.compliance.gdpr.report.service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.report.dto.ActivityReportDto;
import it.reply.compliance.gdpr.report.dto.ActivityReportRequest;
import it.reply.compliance.gdpr.report.mapper.ActivityReportMapper;
import it.reply.compliance.gdpr.report.model.AnswerKey;
import it.reply.compliance.gdpr.report.model.RegistryActivity;
import it.reply.compliance.gdpr.report.model.RegistryActivityAnswer;
import it.reply.compliance.gdpr.report.repository.ActivityAnswerRepository;
import it.reply.compliance.gdpr.report.repository.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityReportMapper activityReportMapper;
    private final ActivityAnswerRepository activityAnswerRepository;

    public ActivityService(ActivityRepository activityRepository, ActivityReportMapper activityReportMapper,
            ActivityAnswerRepository activityAnswerRepository) {
        this.activityRepository = activityRepository;
        this.activityReportMapper = activityReportMapper;
        this.activityAnswerRepository = activityAnswerRepository;
    }

    public Page<ActivityReportDto> getActivityReport(ActivityReportRequest request, Pageable pageable) {
        Specification<RegistryActivity> specification = SpecificationUtils.of(RegistryActivity.class)
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.prefetch("registry.company"))
                .and(SpecificationUtils.hasPropertyIn(request.getYears(), root -> root.join("registry").get("year")))
                .and(SpecificationUtils.hasPropertyIn(request.getRegions(),
                        root -> root.join("registry").join("company").get("regionId")))
                .and(SpecificationUtils.hasPropertyIn(request.getCountries(),
                        root -> root.join("registry").join("company").get("countryId")))
                .and(SpecificationUtils.hasPropertyIn(request.getCompanies(),
                        root -> root.join("registry").join("company").get("id")))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase(request.getCompanyName(),
                        root -> root.join("registry").join("company").get("name")))
                .and(ActivityRepository.hasAnswerWithValueLike(AnswerKey.CLIENT_NAME, request.getClient()))
                .and(ActivityRepository.hasAnswerWithValueLike(AnswerKey.SUPPLIER, request.getSupplier()))
                .and(ActivityRepository.hasAnswerWithValueLike(AnswerKey.TOOL, request.getTool()));
        Page<RegistryActivity> activityPage = activityRepository.findAll(specification, pageable);
        Map<Long, Set<RegistryActivityAnswer>> activityAnswerMap = retrieveAnswers(activityPage);
        return activityPage.map(activity -> addAnswers(activityAnswerMap, activity))
                .map(activityReportMapper::fromEntity);
    }

    private Map<Long, Set<RegistryActivityAnswer>> retrieveAnswers(Page<RegistryActivity> activityPage) {
        Set<Long> activitiesIds = activityPage.stream().map(RegistryActivity::getId).collect(Collectors.toSet());
        Specification<RegistryActivityAnswer> specification = SpecificationUtils.of(RegistryActivityAnswer.class)
                .and(SpecificationUtils.hasPropertyIn("activityId", activitiesIds));
        return activityAnswerRepository.findAll(specification)
                .stream()
                .collect(Collectors.groupingBy(RegistryActivityAnswer::getActivityId, Collectors.toSet()));
    }

    private RegistryActivity addAnswers(Map<Long, Set<RegistryActivityAnswer>> activityAnswerMap,
            RegistryActivity activity) {
        activity.setAnswers(activityAnswerMap.getOrDefault(activity.getId(), Collections.emptySet()));
        return activity;
    }
}
