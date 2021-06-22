package it.reply.compliance.gdpr.registry.dto.campaign;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignDto {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate endDate;
    private Boolean registry;
    private Boolean selfEvaluation;
    private Integer companiesCount;
}
