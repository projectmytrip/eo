package it.reply.compliance.gdpr.authorization.domain;

import java.util.Collection;
import java.util.Collections;

import lombok.Data;

@Data
public class Profile {

    private Collection<String> authorities = Collections.emptyList();
    private Collection<Long> companies = Collections.emptyList();
    private Collection<String> countries = Collections.emptyList();
    private Collection<String> regions = Collections.emptyList();
}
