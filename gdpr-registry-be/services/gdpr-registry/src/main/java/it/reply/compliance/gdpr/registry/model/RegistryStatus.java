package it.reply.compliance.gdpr.registry.model;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Immutable
@Where(clause = "is_deleted=0")
@Table(name = "registry_status")
public class RegistryStatus extends SoftDeletableAndAuditable {
    
    @Id
    @Enumerated(EnumType.STRING)
    private Registry.Status id;

    private String description;

    @OneToMany(mappedBy = "status")
    private Set<Registry> registries = Collections.emptySet();

}
