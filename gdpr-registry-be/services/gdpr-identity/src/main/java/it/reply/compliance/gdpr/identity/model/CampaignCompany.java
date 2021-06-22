package it.reply.compliance.gdpr.identity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "key", callSuper = false)
@Entity
@Where(clause = "is_deleted=0")
public class CampaignCompany {

    @EmbeddedId
    private Key key;

    @Column(name = "campaign_id", insertable = false, updatable = false)
    private Long campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    private String regionId;
    private String countryId;

    @Data
    @Embeddable
    public static class Key implements Serializable {
        private static final long serialVersionUID = -8706926473776302213L;

        @Column(name = "campaign_id")
        private Long campaignId;

        @Column(name = "company_id")
        private Long companyId;
    }
}
