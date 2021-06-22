package it.reply.compliance.gdpr.identity.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import org.apache.catalina.startup.UserConfig;
import org.hibernate.annotations.Where;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "id", "profileId", "isDelegate", "fromDate", "expirationDate" })
@ToString(exclude = "user")
@Entity
@Where(clause = "is_deleted=0")
public class UserProfile extends SoftDeletableAndAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    private String profileId;
    private Boolean isDelegate;
    private Instant fromDate;
    private Instant expirationDate;

    @OneToMany(mappedBy = "userProfile", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<ProfileCompany> companies = new HashSet<>();

    @OneToMany(mappedBy = "userProfile", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<ProfileRegion> regions = new HashSet<>();

    @OneToMany(mappedBy = "userProfile", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<ProfileCountry> countries = new HashSet<>();

    public void setRegions(Collection<String> regions) {
        if (regions == null) {
            this.regions.clear();
            return;
        }
        this.regions.removeIf(r -> !regions.contains(r.getRegionId()));
        regions.stream().map(regionId -> {
            ProfileRegion profileRegion = new ProfileRegion();
            profileRegion.setRegionId(regionId);
            return profileRegion;
        }).forEach(this::addRegion);
    }

    public void setCountries(Collection<String> countries) {
        if (countries == null) {
            this.countries.clear();
            return;
        }
        this.countries.removeIf(c -> !countries.contains(c.getCountryId()));
        countries.stream().map(countryId -> {
            ProfileCountry profileCountry = new ProfileCountry();
            profileCountry.setCountryId(countryId);
            return profileCountry;
        }).forEach(this::addCountry);
    }

    public void setCompanies( Collection<Long> companies) {
        if (companies == null) {
            this.companies.clear();
            return;
        }
        this.companies.removeIf(c -> !companies.contains(c.getCompanyId()));
        companies.stream().map(companyId -> {
            ProfileCompany userCompany = new ProfileCompany();
            userCompany.setCompanyId(companyId);
            userCompany.setUser(this.user);
            return userCompany;
        }).forEach(this::addCompany);
    }

    public void addRegion(ProfileRegion region) {
        region.setUserProfile(this);
        regions.add(region);
    }

    public void addCountry(ProfileCountry country) {
        country.setUserProfile(this);
        countries.add(country);
    }

    public void addCompany(ProfileCompany company) {
        company.setUserProfile(this);
        companies.add(company);
    }

    @Override
    public void setIsDeletedCascade(boolean isDeleted) {
        companies.forEach(company -> company.setIsDeleted(isDeleted));
        regions.forEach(region -> region.setIsDeleted(isDeleted));
        countries.forEach(country -> country.setIsDeleted(isDeleted));
    }
}
