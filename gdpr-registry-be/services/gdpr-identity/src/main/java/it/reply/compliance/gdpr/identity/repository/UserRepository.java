package it.reply.compliance.gdpr.identity.repository;

import java.util.Collection;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.identity.model.User;

public interface UserRepository extends JpaSpecificationRepository<User, Long> {

    static Specification<User> isInRegion(Collection<String> regions) {
        return hasRegionIn(regions).or(SpecificationUtils.hasPropertyIn(regions,
                root -> root.join("profiles", JoinType.LEFT)
                        .join("companies", JoinType.LEFT)
                        .join("company", JoinType.LEFT)
                        .get("regionId")));
    }

    static Specification<User> isInCountry(Collection<String> countries) {
        return hasCountryIn(countries).or(SpecificationUtils.hasPropertyIn(countries,
                root -> root.join("profiles", JoinType.LEFT)
                        .join("companies", JoinType.LEFT)
                        .join("company", JoinType.LEFT)
                        .get("countryId")));
    }

    static Specification<User> hasRegion(String regionId) {
        return SpecificationUtils.<User, String>hasProperty(regionId,
                root -> root.join("profiles", JoinType.LEFT).join("regions", JoinType.LEFT).get("regionId"));
    }

    static Specification<User> hasCountry(String countryId) {
        return SpecificationUtils.<User, String>hasProperty(countryId,
                root -> root.join("profiles", JoinType.LEFT).join("countries", JoinType.LEFT).get("countryId"));
    }

    static Specification<User> hasRegionIn(Collection<String> regionsIds) {
        return SpecificationUtils.hasPropertyIn(regionsIds,
                root -> root.join("profiles", JoinType.LEFT).join("regions", JoinType.LEFT).get("regionId"));
    }

    static Specification<User> hasCountryIn(Collection<String> countriesIds) {
        return SpecificationUtils.hasPropertyIn(countriesIds,
                root -> root.join("profiles", JoinType.LEFT).join("countries", JoinType.LEFT).get("countryId"));
    }

    static Specification<User> hasProfile(String profileId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.join("profiles", JoinType.LEFT).get("profileId"), profileId);
    }
}
