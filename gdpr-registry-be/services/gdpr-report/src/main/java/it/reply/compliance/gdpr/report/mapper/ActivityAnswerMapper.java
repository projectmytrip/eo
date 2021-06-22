package it.reply.compliance.gdpr.report.mapper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.gdpr.report.dto.ActivityAnswerDto;
import it.reply.compliance.gdpr.report.dto.AnswerDetailsDto;
import it.reply.compliance.gdpr.report.model.RegistryActivityAnswer;

@Mapper(componentModel = "spring")
public interface ActivityAnswerMapper {

    @Mapping(target = "text", source = "answer")
    AnswerDetailsDto fromEntity(RegistryActivityAnswer answer);

    default Collection<ActivityAnswerDto> fromEntities(Collection<RegistryActivityAnswer> answers) {
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

    private ActivityAnswerDto fromEntityMap(Map.Entry<String, List<RegistryActivityAnswer>> questionEntry) {
        ActivityAnswerDto activityAnswerDto = new ActivityAnswerDto();
        activityAnswerDto.setQuestionKey(questionEntry.getKey());
        activityAnswerDto.setAnswerDetails(fromEntities(questionEntry));
        return activityAnswerDto;
    }

    private List<AnswerDetailsDto> fromEntities(Map.Entry<String, List<RegistryActivityAnswer>> questionEntry) {
        return questionEntry.getValue()
                .stream()
                .map(this::fromEntity)
                .sorted(Comparator.comparing(AnswerDetailsDto::getPriority))
                .collect(Collectors.toList());
    }
}
