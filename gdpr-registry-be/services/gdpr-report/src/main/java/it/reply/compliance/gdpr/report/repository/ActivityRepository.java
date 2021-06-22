package it.reply.compliance.gdpr.report.repository;

import java.util.Locale;
import java.util.Objects;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.report.model.RegistryActivity;

public interface ActivityRepository extends JpaSpecificationRepository<RegistryActivity, Long> {

    String WILDCARD_REQUEST = "\\*";
    String WILDCARD_DB = "%";

    static Specification<RegistryActivity> hasAnswerWithValueLike(String questionKey, String value) {
        Objects.requireNonNull(questionKey, "Question Key cannot be null");
        if (value == null) {
            return SpecificationUtils.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> answers = root.join("answers");
            Path<String> questionKeyPath = answers.get("questionKey");
            Path<String> answerPath = answers.get("answer");
            String likeValue = value.replaceAll(WILDCARD_REQUEST, WILDCARD_DB).toUpperCase(Locale.ROOT);
            return criteriaBuilder.and(criteriaBuilder.equal(questionKeyPath, questionKey),
                    criteriaBuilder.like(criteriaBuilder.upper(answerPath), likeValue));
        };
    }
}
