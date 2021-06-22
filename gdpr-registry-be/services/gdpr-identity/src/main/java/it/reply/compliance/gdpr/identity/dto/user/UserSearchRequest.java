package it.reply.compliance.gdpr.identity.dto.user;

import java.util.Collection;

import it.reply.compliance.gdpr.identity.model.User;
import lombok.Data;

@Data
public class UserSearchRequest {

    private String name;
    private String surname;
    private Boolean isDelegate;
    private Collection<User.Status> statuses;
    private Collection<String> regions;
    private Collection<String> countries;
    private Collection<String> jobTitles;
    private Collection<Long> companies;
    private Collection<String> roles;
}
