package it.reply.compliance.gdpr.report.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class ActivityReportDto {

    private Long id;
    private String status;
    private RegistryDto registry;
    private Collection<ActivityAnswerDto> answers;
}
