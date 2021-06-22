package it.reply.compliance.gdpr.authorization.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "key")
@ToString(exclude = "profile")
@NoArgsConstructor
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class ProfilePermission {

    @EmbeddedId
    private Key key;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "permission_id", insertable = false, updatable = false)
    private String permissionId;

    @PrePersist
    private void prePersist() {
        if (key == null) {
            key = new Key();
            key.setProfileId(profile.getId());
            key.setPermissionId(permissionId);
        }
    }

    @Data
    @Embeddable
    public static class Key implements Serializable {
        private static final long serialVersionUID = -4000842852980618941L;

        @Column(name = "profile_id")
        private String profileId;

        @Column(name = "permission_id")
        private String permissionId;
    }
}
