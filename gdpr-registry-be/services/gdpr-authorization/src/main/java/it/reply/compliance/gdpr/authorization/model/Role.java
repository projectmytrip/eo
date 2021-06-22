package it.reply.compliance.gdpr.authorization.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "role")
@Immutable
@Where(clause = "is_deleted=0")
public class Role {

    @Id
    private String id;
    private String description;
}
