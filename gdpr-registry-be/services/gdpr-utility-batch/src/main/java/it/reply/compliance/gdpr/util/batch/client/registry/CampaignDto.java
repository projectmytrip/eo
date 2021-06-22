package it.reply.compliance.gdpr.util.batch.client.registry;

import java.time.LocalDate;
import java.util.Collection;

import lombok.Data;

@Data
public class CampaignDto {
    
    private Long id;
    private String name;
    private Boolean registry;
    private Boolean selfEvaluation;
    private LocalDate dueDate;
    private Collection<Long> companies;
}
