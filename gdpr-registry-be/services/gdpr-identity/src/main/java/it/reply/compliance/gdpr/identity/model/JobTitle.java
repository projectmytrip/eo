package it.reply.compliance.gdpr.identity.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
public class JobTitle extends SoftDeletableAndAuditable {

    @Id
    private String id;
    private String name;
    private String description;
}
