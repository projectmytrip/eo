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

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false, of = {"userProfileId", "companyId"})
@ToString(exclude = {"company", "user"})
@Data
@Entity
public class ProfileCompany extends SoftDeletableAndAuditable {

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_profile_id", insertable = false, updatable = false)
    private Long userProfileId;
    @Column(name = "company_id", insertable = false, updatable = false)
    private Long companyId;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @PrePersist
    private void prePersist() {
        if (key == null) {
            key = new Key();
            key.setCompanyId(companyId);
            key.setUserProfileId(userProfile.getId());
        }
    }

    @Data
    @Embeddable
    public static class Key implements Serializable {

        private static final long serialVersionUID = -7159723508158473167L;
        
        @Column(name = "user_profile_id")
        private Long userProfileId;
        @Column(name = "company_id")
        private Long companyId;
    }
}
