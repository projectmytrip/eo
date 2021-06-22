package it.reply.compliance.gdpr.identity.dto.country;

import java.util.List;

import lombok.Data;

@Data
public class CountryRequest {

    private List<String> regions;
    private String startWith;
}
