package it.reply.compliance.gdpr.registry.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.registry.dto.registryactivityanswer.RegistryActivityAnswerDto;
import it.reply.compliance.gdpr.registry.model.RegistryActivityAnswer;

@Mapper(componentModel = "spring")
public interface RegistryActivityAnswerMapper {

    RegistryActivityAnswerDto fromEntity(RegistryActivityAnswer registryActivityAnswer);

    Collection<RegistryActivityAnswerDto> fromEntities(Collection<RegistryActivityAnswer> registryActivityAnswers);

    RegistryActivityAnswer toEntity(RegistryActivityAnswerDto registryActivityAnswerDto);

    Collection<RegistryActivityAnswer> toEntities(Collection<RegistryActivityAnswerDto> registryActivityAnswersDto);

}
