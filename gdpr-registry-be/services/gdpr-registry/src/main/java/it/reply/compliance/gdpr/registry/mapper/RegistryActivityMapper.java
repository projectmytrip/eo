package it.reply.compliance.gdpr.registry.mapper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.commons.map.MappingUtils;
import it.reply.compliance.gdpr.registry.dto.registryactivity.RegistryActivityDto;
import it.reply.compliance.gdpr.registry.dto.registryactivityanswer.RegistryActivityAnswerDetailDto;
import it.reply.compliance.gdpr.registry.dto.registryactivityanswer.RegistryActivityAnswerDto;
import it.reply.compliance.gdpr.registry.model.RegistryActivity;
import it.reply.compliance.gdpr.registry.model.RegistryActivityAnswer;

@Mapper(componentModel = "spring")
public interface RegistryActivityMapper {

    RegistryActivityDto fromEntity(RegistryActivity registryActivity);

    Collection<RegistryActivityDto> fromEntities(Collection<RegistryActivity> registryActivities);

    @Mapping(target = "id", ignore = true)
    RegistryActivity toEntity(RegistryActivityDto registryActivityDto);

    Collection<RegistryActivity> toEntities(Collection<RegistryActivityDto> registryActivitiesDto);

    @Mapping(target = "text", source = "answer")
    RegistryActivityAnswerDetailDto fromEntity(RegistryActivityAnswer answer);

    default Collection<RegistryActivityAnswer> toEntities(List<RegistryActivityAnswerDto> list) {
        if (list == null) {
            return null;
        }
        return list.stream().flatMap(this::unrollDetails).collect(Collectors.toList());
    }

    default List<RegistryActivityAnswerDto> fromAnswerEntities(Collection<RegistryActivityAnswer> answers) {
        if (answers == null) {
            return null;
        }
        return answers.stream()
                .collect(Collectors.groupingBy(RegistryActivityAnswer::getQuestionKey))
                .entrySet()
                .stream()
                .map(this::fromEntityMap)
                .collect(Collectors.toList());
    }

    default RegistryActivity update(RegistryActivity toUpdate, RegistryActivity updating) {
        if (toUpdate == null || updating == null) {
            return toUpdate;
        }
        MappingUtils.setIfHasValue(toUpdate::setAnswers, updating.getAnswers());
        return toUpdate;
    }

    private RegistryActivityAnswerDto fromEntityMap(Map.Entry<String, List<RegistryActivityAnswer>> questionEntry) {
        RegistryActivityAnswerDto activityAnswerDto = new RegistryActivityAnswerDto();
        activityAnswerDto.setQuestionKey(questionEntry.getKey());
        activityAnswerDto.setAnswerDetails(fromEntities(questionEntry));
        return activityAnswerDto;
    }

    private List<RegistryActivityAnswerDetailDto> fromEntities(
            Map.Entry<String, List<RegistryActivityAnswer>> questionEntry) {
        return questionEntry.getValue()
                .stream()
                .map(this::fromEntity)
                .sorted(Comparator.comparing(RegistryActivityAnswerDetailDto::getPriority))
                .collect(Collectors.toList());
    }

    private Stream<RegistryActivityAnswer> unrollDetails(RegistryActivityAnswerDto registryActivityAnswerDto) {
        if (registryActivityAnswerDto == null) {
            return null;
        }
        List<RegistryActivityAnswerDetailDto> answerDetails = registryActivityAnswerDto.getAnswerDetails();
        return IntStream.range(0, answerDetails.size()).mapToObj(index -> {
            RegistryActivityAnswerDetailDto detailDto = answerDetails.get(index);
            RegistryActivityAnswer entity = new RegistryActivityAnswer();
            entity.setQuestionKey(registryActivityAnswerDto.getQuestionKey());
            entity.setAnswerKey(detailDto.getAnswerKey());
            entity.setAnswer(detailDto.getText());
            entity.setPriority(index);
            entity.setActivityId(registryActivityAnswerDto.getActivityId());
            return entity;
        });
    }
}
