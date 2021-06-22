package it.reply.compliance.gdpr.authorization.domain;

import java.util.Collection;
import java.util.Collections;

import lombok.Data;

@Data
public class GdprSubject {

    private Long id;
    private String username;
    private Collection<Profile> profiles = Collections.emptyList();
}
