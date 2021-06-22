package it.reply.compliance.gdpr.authorization.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class Permission {

    @Id
    private String id;
    private String description;
}
