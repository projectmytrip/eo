package it.reply.compliance.gdpr.campaign.model;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class Company {

    @Id
    private Long id;
    private String regionId;
    private String countryId;

    @ToString.Exclude
    @OneToMany(mappedBy = "company")
    private Set<CampaignCompany> campaigns = Collections.emptySet();
}
