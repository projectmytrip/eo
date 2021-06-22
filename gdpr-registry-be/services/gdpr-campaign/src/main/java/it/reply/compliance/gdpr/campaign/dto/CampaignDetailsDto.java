package it.reply.compliance.gdpr.campaign.dto;

import java.time.Instant;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CampaignDetailsDto extends CampaignDto {

    @JsonProperty("lastModifiedBy")
    private UserDto userModifiedBy;
    private Instant lastModifiedDateTime;
    private Collection<Long> companies;
}
