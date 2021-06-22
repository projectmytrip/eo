package it.reply.compliance.gdpr.identity.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
public class Region {

    @Id
    private String id;
    private String description;
}
