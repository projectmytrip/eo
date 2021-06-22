package it.reply.compliance.gdpr.util.mail.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class EmailTemplate {

    @Id
    private String id;
    private String description;
    private String subjectTemplate;
    @Lob
    private String template;
}
