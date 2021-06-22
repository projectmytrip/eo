package it.reply.compliance.gdpr.identity.dto.region;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionResponse {

    private Collection<RegionDto> regions;
}
