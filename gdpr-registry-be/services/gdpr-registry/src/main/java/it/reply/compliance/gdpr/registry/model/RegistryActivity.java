package it.reply.compliance.gdpr.registry.model;

import java.util.Collection;
import java.util.LinkedList;

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
@Table(name = "registry_activity")
public class RegistryActivity extends SoftDeletableAndAuditable {

    public enum Status {
        OPEN,
        WAITING,
        EDITABLE,
        CONFIRMED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registry_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Registry registry;

    @Column(name = "registry_id")
    private Long registryId;

    @OneToMany(mappedBy = "activity", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Collection<RegistryActivityAnswer> answers;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    private void prePersist() {
        if (registryId == null) {
            registryId = registry.getId();
        }
        if (status == null) {
            status = Status.OPEN;
        }
    }

    public void setAnswers(Collection<RegistryActivityAnswer> answers) {
        if (answers != null) {
            initializeAnswers();
            this.answers.clear();
            answers.forEach(answer -> {
                answer.setActivity(this);
                this.answers.add(answer);
            });
        }
    }

    public void setLoadedAnswers(Collection<RegistryActivityAnswer> answers) {
        this.answers = answers;
    }

    public boolean isModifiable() {
        return status != Status.CONFIRMED;
    }

    private void initializeAnswers() {
        if (this.answers == null) {
            this.answers = new LinkedList<>();
        }
    }

}
