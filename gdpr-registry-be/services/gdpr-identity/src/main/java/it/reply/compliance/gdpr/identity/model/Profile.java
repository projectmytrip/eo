package it.reply.compliance.gdpr.identity.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
public class Profile {

    public static final String DPO = "DPO";
    public static final String COMPANY_SUFFIX = "_COMPANY";
    public static final String COUNTRY_SUFFIX = "_COUNTRY";
    public static final String REGION_SUFFIX = "_REGION";

    @Id
    private String id;
    private String description;
    private String roleId;
}
