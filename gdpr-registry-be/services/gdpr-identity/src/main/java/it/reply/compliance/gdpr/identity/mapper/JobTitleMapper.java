package it.reply.compliance.gdpr.identity.mapper;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.identity.dto.JobTitleDto;
import it.reply.compliance.gdpr.identity.model.JobTitle;

@Mapper(componentModel = "spring")
public interface JobTitleMapper {

    JobTitleDto fromEntity(JobTitle jobTitle);
}
