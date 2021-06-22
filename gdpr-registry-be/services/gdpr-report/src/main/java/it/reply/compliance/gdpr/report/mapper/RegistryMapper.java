package it.reply.compliance.gdpr.report.mapper;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.report.dto.ActivityReportDto;
import it.reply.compliance.gdpr.report.dto.RegistryDto;
import it.reply.compliance.gdpr.report.model.Registry;
import it.reply.compliance.gdpr.report.model.RegistryActivity;

@Mapper(componentModel = "spring")
public interface RegistryMapper {

    RegistryDto fromEntity(Registry registry);
}
