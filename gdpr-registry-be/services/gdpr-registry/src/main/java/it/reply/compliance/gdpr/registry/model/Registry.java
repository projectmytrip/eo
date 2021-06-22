package it.reply.compliance.gdpr.registry.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
@Table(name = "registry")
public class Registry extends SoftDeletableAndAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_version", referencedColumnName = "id", insertable = false, updatable = false)
    private RegistryTemplate template;

    @Column(name = "template_version")
    private Long templateVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RegistryStatus status;

    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private Status statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany(mappedBy = "registry", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Collection<RegistryActivity> activities;

    @OneToMany(mappedBy = "registry", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Collection<RegistryStatusHistory> statusHistory;

    public void setActivities(Collection<RegistryActivity> activities) {
        if (activities != null) {
            initializeActivities();
            activities.forEach(activity -> {
                activity.setRegistry(this);
                this.activities.add(activity);
            });
        }
    }

    public Collection<RegistryActivity> getActivities() {
        initializeActivities();
        return this.activities;
    }

    private void initializeActivities() {
        if (this.activities == null) {
            this.activities = new ArrayList<>();
        }
    }

    public void addStatusHistory(RegistryStatusHistory newStatusHistory) {
        if (newStatusHistory != null) {
            if (statusHistory == null) {
                initializeStatusHistory();
            }
            newStatusHistory.setRegistry(this);
            this.statusHistory.add(newStatusHistory);
        }
    }

    public void setStatusHistory(Collection<RegistryStatusHistory> statusHistory) {
        if (statusHistory != null) {
            initializeStatusHistory();
            statusHistory.forEach(currentStatusHistory -> {
                currentStatusHistory.setRegistry(this);
                this.statusHistory.add(currentStatusHistory);
            });
        }
    }

    private void initializeStatusHistory() {
        if (this.statusHistory == null) {
            this.statusHistory = new ArrayList<>();
        }
    }

    public enum Status {
        CREATED,
        OPEN,
        IN_PROGRESS,
        COMPANY_VALIDATED,
        DPO_VALIDATED,
        ARCHIVED,
    }

    public static final Map<Status, Set<Status>> ALLOWED_STATUS_CHANGES = Map.of(
            // @formatter:off
            Status.CREATED, Set.of(Status.OPEN, Status.IN_PROGRESS), 
            Status.OPEN, Set.of(Status.IN_PROGRESS), 
            Status.IN_PROGRESS, Set.of(Status.COMPANY_VALIDATED), 
            Status.COMPANY_VALIDATED, Set.of(Status.DPO_VALIDATED, Status.IN_PROGRESS),
            Status.DPO_VALIDATED, Set.of(Status.ARCHIVED)
            // formatter:on
            );
}
