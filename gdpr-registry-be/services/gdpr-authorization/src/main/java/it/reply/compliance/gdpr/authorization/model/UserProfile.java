package it.reply.compliance.gdpr.authorization.model;

import java.time.Instant;
import java.util.Collections;
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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "user")
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class UserProfile {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Profile profile;
    @Column(name = "profile_id", insertable = false, updatable = false)
    private String profileId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    private Boolean isDelegate;
    private Instant fromDate;
    private Instant expirationDate;

    @OneToMany(mappedBy = "userProfile")
    private Set<ProfileCompany> companies = Collections.emptySet();

    @OneToMany(mappedBy = "userProfile")
    private Set<ProfileRegion> regions = Collections.emptySet();

    @OneToMany(mappedBy = "userProfile")
    private Set<ProfileCountry> countries = Collections.emptySet();
}
