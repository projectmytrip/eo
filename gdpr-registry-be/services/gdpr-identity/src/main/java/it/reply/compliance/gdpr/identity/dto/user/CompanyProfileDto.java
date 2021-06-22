package it.reply.compliance.gdpr.identity.dto.user;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyProfileDto {

    private String id;
    private String description;
    private String roleId;
    private Boolean isDelegate;
    private Instant fromDate;
    private Instant expirationDateTime;
}
