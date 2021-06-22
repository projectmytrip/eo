package it.reply.compliance.gdpr.registry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "registry_activity_answer")
public class RegistryActivityAnswer extends SoftDeletableAndAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RegistryActivity activity;

    @Column(name = "activity_id")
    private Long activityId;

    private String questionKey;

    private String answer;

    private Integer priority;

    private Long answerKey;

    @PrePersist
    private void prePersist() {
        if (activityId == null) {
            activityId = activity.getId();
        }
    }


}
