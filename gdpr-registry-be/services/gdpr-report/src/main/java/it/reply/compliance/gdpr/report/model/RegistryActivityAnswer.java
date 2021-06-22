package it.reply.compliance.gdpr.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class RegistryActivityAnswer {

    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RegistryActivity activity;
    @Column(name = "activity_id")
    private Long activityId;
    private String questionKey;
    private String answer;
    private Long priority;
    private Long answerKey;
}
