package it.reply.compliance.gdpr.util.mail.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String templateId;
    @Column(name = "\"from\"")
    private String from;
    @Column(name = "\"to\"")
    private String to;
    @Column(name = "cc")
    private String cc;
    @Column(name = "bcc")
    private String bcc;
    @Column(name = "\"date\"")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Lob
    private String templateValues;
    private Boolean customText;
    @Lob
    private String text;
    @Lob
    @Column(name = "\"error\"")
    private String error;
    @Lob
    private String attachments;
}
