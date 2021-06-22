package it.reply.compliance.gdpr.campaign.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Integer year;
    private Boolean registry;
    private Boolean selfEvaluation;
    private Integer companiesCount;
    private Instant createdDateTime;
    @JsonProperty("createdBy")
    private UserDto userCreatedBy;
}
