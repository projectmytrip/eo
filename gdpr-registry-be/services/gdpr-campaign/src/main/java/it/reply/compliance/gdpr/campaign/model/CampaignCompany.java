package it.reply.compliance.gdpr.campaign.model;

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

import it.reply.compliance.commons.persistence.audit.SoftDeletableAndAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"companyId"}, callSuper = false)
@ToString(exclude = "campaign")
@Entity
@Where(clause = "is_deleted=0")
public class CampaignCompany extends SoftDeletableAndAuditable {

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Campaign campaign;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;      
    
    @Column(name = "company_id", insertable = false, updatable = false)
    private Long companyId;

    private String regionId;
    private String countryId;

    @PrePersist
    private void prePersist() {
        if (key == null) {
            key = new Key();
            key.setCampaignId(campaign.getId());
            key.setCompanyId(companyId);
        }
    }

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
