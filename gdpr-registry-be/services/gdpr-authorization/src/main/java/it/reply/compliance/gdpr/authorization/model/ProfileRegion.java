package it.reply.compliance.gdpr.authorization.model;

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
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "key")
@ToString(exclude = "userProfile")
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class ProfileRegion {

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserProfile userProfile;

    @Column(name = "region_id", insertable = false, updatable = false)
    private String regionId;

    @Data
    @Embeddable
    public static class Key implements Serializable {
        private static final long serialVersionUID = 719043601845822656L;

        @Column(name = "user_profile_id")
        private Long userProfileId;

        @Column(name = "region_id")
        private String regionId;
    }
}
