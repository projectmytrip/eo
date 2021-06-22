package it.reply.compliance.gdpr.registry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
@Table(name = "registry_status_history")
public class RegistryStatusHistory extends SoftDeletableAndAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registry_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Registry registry;

    @Column(name = "registry_id")
    private Long registryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_status", referencedColumnName = "id", insertable = false, updatable = false)
    private RegistryStatus oldStatus;

    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    private Registry.Status oldStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_status", referencedColumnName = "id", insertable = false, updatable = false)
    private RegistryStatus newStatus;

    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    private Registry.Status newStatusId;

    @PrePersist
    private void prePersist() {
        if (registryId == null && registry != null) {
            registryId = registry.getId();
        }
    }

}
