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

@Data
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class Registry {

    @Id
    private Long id;
    private Long year;
    private String statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany(mappedBy = "registry")
    private Set<RegistryActivity> activities;
}
