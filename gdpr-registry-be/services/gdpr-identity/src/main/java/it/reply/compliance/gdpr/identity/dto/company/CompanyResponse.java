package it.reply.compliance.gdpr.identity.dto.company;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponse {

    private Collection<CompanyDto> companies;
}
