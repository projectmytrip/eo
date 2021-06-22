package it.reply.compliance.gdpr.identity.dto.country;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryResponse {

    private Collection<CountryDto> countries;
}
