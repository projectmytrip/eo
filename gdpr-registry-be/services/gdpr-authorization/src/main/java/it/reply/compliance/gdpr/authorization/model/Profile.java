package it.reply.compliance.gdpr.authorization.model;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class Profile {

    @Id
    private String id;
    private String description;
    private String roleId;
    @OneToMany(mappedBy = "profile")
    private Set<ProfilePermission> permissions = Collections.emptySet();
}
