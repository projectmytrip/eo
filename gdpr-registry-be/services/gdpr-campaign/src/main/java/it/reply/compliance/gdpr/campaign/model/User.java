package it.reply.compliance.gdpr.campaign.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
public class User {
    
    @Id
    private Long id;
    private String name;
    private String surname;
    private String username;
}
