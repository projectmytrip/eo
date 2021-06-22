package it.reply.compliance.gdpr.campaign.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.campaign.model.Campaign;

public interface CampaignRepository extends JpaSpecificationRepository<Campaign, Long> {

    static <T> Specification<Campaign> hasPropertyInCompany(String propertyName, Collection<T> values) {
        if (values == null) {
            return SpecificationUtils.conjunction();
        }
        if (values.isEmpty()) {
            return SpecificationUtils.disjunction();
        }
        return (root, query, criteriaBuilder) -> root.join("companies").get(propertyName).in(values);
    }

    static <T> Specification<Campaign> isOpen(Boolean isOpen) {
        if (isOpen == null) {
            return SpecificationUtils.conjunction();
        }
        LocalDate today = LocalDate.now();
        Specification<Campaign> campaignSpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), today),
                criteriaBuilder.greaterThanOrEqualTo(root.get("dueDate"), today));
        return isOpen ? campaignSpecification : Specification.not(campaignSpecification);
    }
}
