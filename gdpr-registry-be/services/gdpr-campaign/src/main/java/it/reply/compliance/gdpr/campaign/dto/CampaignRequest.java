package it.reply.compliance.gdpr.campaign.dto;

import java.util.Collection;
import java.util.List;

import lombok.Data;

@Data
public class CampaignRequest {

    private String name;
    private Boolean registry;
    private Boolean selfEvaluation;
    private List<Integer> years;
    private Boolean open;
    private Collection<String> regions;
    private Collection<String> countries;
    private Collection<Long> companies;
}
