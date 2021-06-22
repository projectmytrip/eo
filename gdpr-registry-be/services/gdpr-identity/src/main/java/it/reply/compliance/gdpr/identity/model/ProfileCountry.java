package it.reply.compliance.gdpr.identity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Where;

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "countryId", callSuper = false)
@ToString(exclude = "userProfile")
@Entity
@Where(clause = "is_deleted=0")
public class ProfileCountry extends SoftDeletableAndAuditable {

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserProfile userProfile;

    @Column(name = "country_id", insertable = false, updatable = false)
    private String countryId;

    @PrePersist
    private void prePersist() {
        if (key == null) {
            key = new Key();
            key.setUserProfileId(userProfile.getId());
            key.setCountryId(countryId);
        }
    }

    @Data
    @Embeddable
    public static class Key implements Serializable {
        private static final long serialVersionUID = 965518540715036769L;

        @Column(name = "user_profile_id")
        private Long userProfileId;

        @Column(name = "country_id")
        private String countryId;
    }
}
