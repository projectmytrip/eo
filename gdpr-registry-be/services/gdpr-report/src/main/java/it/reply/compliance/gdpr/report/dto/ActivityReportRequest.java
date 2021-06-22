package it.reply.compliance.gdpr.report.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class ActivityReportRequest {

    private Collection<Integer> years;
    private Collection<String> regions;
    private Collection<String> countries;
    private Collection<Long> companies;
    private String companyName;
    private String client;
    private String supplier;
    private String tool;
}
