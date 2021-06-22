package it.reply.compliance.gdpr.identity.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Where;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
public class User extends SoftDeletableAndAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title", referencedColumnName = "id")
    private JobTitle jobTitle;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<UserProfile> profiles = Collections.emptySet();

    public void addProfile(UserProfile profile) {
        validateProfile(profile);
        profile.setUser(this);
        profiles.add(profile);
    }

    private void verifyUserHasOnlyOneCompany(UserProfile userProfile) {
        if (notEmpty(userProfile.getRegions())) {
            throw new IllegalArgumentException(userProfile.getProfile().getRoleId() + " cannot have region associated");
        }
        if (notEmpty(userProfile.getCountries())) {
            throw new IllegalArgumentException(
                    userProfile.getProfile().getRoleId() + " cannot have countries associated");
        }
        if (!notEmpty(userProfile.getCompanies())) {
            throw new IllegalArgumentException(
                    userProfile.getProfile().getRoleId() + " must have one company associated");
        }
        if (userProfile.getCompanies().size() > 1) {
            throw new IllegalArgumentException(String.format("%s must have only one company associated, found %d",
                    userProfile.getProfile().getRoleId(), userProfile.getCompanies().size()));
        }
    }

    private void validateProfile(UserProfile userProfile) {
        if (!Role.SINGLE_COMPANY_ROLES.contains(userProfile.getProfile().getRoleId())) {
            verifyUserHasOnlyOneCompany(userProfile);
        }
    }

    private <T> boolean notEmpty(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    @Override
    protected void setIsDeletedCascade(boolean isDeleted) {
        profiles.forEach(profile -> profile.setIsDeleted(isDeleted));
    }

    public enum Status {
        ENABLED,
        DISABLED;

        private final static Set<String> statuses = Arrays.stream(Status.values())
                .map(Enum::name)
                .collect(Collectors.toUnmodifiableSet());

        public static boolean contains(String status) {
            return statuses.contains(status.toUpperCase(Locale.ROOT));
        }
    }
}
