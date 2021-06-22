package it.reply.compliance.gdpr.identity.dto.user;

import java.time.Instant;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    private String id;
    private String description;
    private String roleId;
    private Boolean isDelegate;
    private Instant expirationDateTime;
    private Collection<String> regions;
    private Collection<String> countries;
    private Collection<UserCompanyDto> companies;
}
