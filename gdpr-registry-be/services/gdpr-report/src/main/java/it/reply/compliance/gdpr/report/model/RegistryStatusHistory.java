package it.reply.compliance.gdpr.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class RegistryStatusHistory extends SoftDeletableAndAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registry_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Registry registry;
    @Column(name = "registry_id")
    private Long registryId;
    private String oldStatus;
    private String newStatus;
}
