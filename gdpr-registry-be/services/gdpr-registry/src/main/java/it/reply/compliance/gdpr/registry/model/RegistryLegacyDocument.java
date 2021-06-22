package it.reply.compliance.gdpr.registry.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id", callSuper = false)
@Data
@Entity
@Immutable
@Where(clause = "is_deleted=0")
public class RegistryLegacyDocument {

    @Id
    private Long id;
    private Integer year;
    private Boolean registry;
    private Boolean selfEvaluation;
    private String countryId;
    private String regionId;
    private Long companyId;
    private String companyCode;
    private String companyName;
    private LocalDate creationDate;
    private String filename;
    private String path;
}
