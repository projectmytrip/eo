package it.reply.compliance.gdpr.identity.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table
@Where(clause = "is_deleted=0")
public class Role {

    public static final String DPO = "DPO";
    public static final String ADMIN = "ADMIN";
    public static final Set<String> MULTI_LEVEL_ROLES = Set.of(DPO);
    public static final Set<String> SINGLE_COMPANY_ROLES = Set.of(DPO, ADMIN);
    
    @Id
    private String id;
    private String description;
}
