package it.reply.compliance.gdpr.report.mapper;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.report.dto.ActivityReportDto;
import it.reply.compliance.gdpr.report.model.RegistryActivity;

@Mapper(componentModel = "spring", uses = { ActivityAnswerMapper.class, RegistryMapper.class })
public interface ActivityReportMapper {

    ActivityReportDto fromEntity(RegistryActivity activity);
}
