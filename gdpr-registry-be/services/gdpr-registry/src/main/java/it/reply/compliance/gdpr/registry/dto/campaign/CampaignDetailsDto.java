package it.reply.compliance.gdpr.registry.dto.campaign;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CampaignDetailsDto extends CampaignDto {

    private String RegionId;
    private String CountryId;
    private Collection<Long> companies;
}
