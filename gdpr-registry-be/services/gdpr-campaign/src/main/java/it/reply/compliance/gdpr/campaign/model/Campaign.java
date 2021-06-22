package it.reply.compliance.gdpr.campaign.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
public class Campaign extends SoftDeletableAndAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer year;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate endDate;
    private Boolean registry;
    private Boolean selfEvaluation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy", referencedColumnName = "id", insertable = false, updatable = false)
    private User userCreatedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "id", insertable = false, updatable = false)
    private User userModifiedBy;
    @OneToMany(mappedBy = "campaign", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<CampaignCompany> companies;

    public void setCompanies(Collection<CampaignCompany> companies) {
        if (companies != null) {
            initializeCompanies();
            this.companies.removeIf(company -> !companies.contains(company));
            companies.forEach(company -> {
                company.setCampaign(this);
                this.companies.add(company);
            });
        }
    }

    private void initializeCompanies() {
        if (this.companies == null) {
            this.companies = new HashSet<>();
        }
    }

    @Override
    protected void setIsDeletedCascade(boolean isDeleted) {
        companies.forEach(company -> company.setIsDeleted(isDeleted));
    }
}
