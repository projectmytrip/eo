package it.reply.compliance.gdpr.identity.dto.user;

import java.time.Instant;
import java.util.Collection;

import lombok.Data;

@Data
public class UserProfileAssignment {

    private String roleId;
    private Boolean isDelegate;
    private Boolean companyValidate;
    private Instant fromDateTime;
    private Instant expirationDateTime;
    private Collection<String> regions;
    private Collection<String> countries;
    private Collection<Long> companies;
}
