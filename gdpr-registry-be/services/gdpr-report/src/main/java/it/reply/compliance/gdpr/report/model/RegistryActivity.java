package it.reply.compliance.gdpr.report.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Immutable
@Where(clause = "is_deleted=0")
public class RegistryActivity {

    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registry_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Registry registry;
    @Column(name = "registry_id")
    private Long registryId;
    private String status;
    @OneToMany(mappedBy = "activity")
    private Set<RegistryActivityAnswer> answers;
}
