package it.reply.compliance.gdpr.identity.dto.company;

import java.util.Collection;
import java.util.List;

import lombok.Data;

@Data
public class CompanyRequest {

    private Boolean withDpos;
    private List<String> regions;
    private List<String> countries;
    private String name;
    private Collection<Integer> years;
    private Collection<Long> ids;
}
