package it.reply.compliance.commons.persistence;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private static final String WILDCARD_REQUEST = "\\*";
    private static final String WILDCARD_DB = "%";
    private static final Specification<?> CONJUNCTION = (root, query, cb) -> cb.conjunction();
    private static final Specification<?> DISJUNCTION = (root, query, cb) -> cb.disjunction();

    private SpecificationUtils() {
    }

    public static <T> Specification<T> prefetch(String attributeName) {
        return (root, query, cb) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                String[] attributes = attributeName.split("\\.");
                Fetch<Object, Object> objectFetch = root.fetch(attributes[0], JoinType.LEFT);
                for (int i = 1, l = attributes.length; i < l; i++) {
                    objectFetch = objectFetch.fetch(attributes[i], JoinType.LEFT);
                }
            }
            return cb.conjunction();
        };
    }

    public static <T> Specification<T> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.conjunction();
        };
    }

    public static <T> Specification<T> of(Class<T> clazz) {
        return conjunction();
    }

    public static <T, U> Specification<T> hasId(U id) {
        if (id == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    public static <T, U> Specification<T> hasProperty(String propertyName, U propertyValue) {
        return hasProperty(propertyValue, root -> root.get(propertyName));
    }

    public static <T, U> Specification<T> hasProperty(U propertyValue, Function<Root<T>, Path<T>> mapper) {
        if (propertyValue == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.equal(mapper.apply(root), propertyValue);
    }

    public static <T> Specification<T> hasPropertyLikeIgnoreCase(String propertyName, String propertyValue) {
        if (propertyValue == null) {
            return conjunction();
        }
        String likeValue = propertyValue.replaceAll(WILDCARD_REQUEST, WILDCARD_DB).toUpperCase(Locale.ROOT);
        return (root, query, cb) -> cb.like(cb.upper(root.get(propertyName)), likeValue);
    }

    public static <T> Specification<T> hasPropertyLikeIgnoreCase(String propertyValue,
            Function<Root<?>, Path<String>> mapper) {
        if (propertyValue == null) {
            return conjunction();
        }
        String likeValue = propertyValue.replaceAll(WILDCARD_REQUEST, WILDCARD_DB).toUpperCase(Locale.ROOT);
        return (root, query, cb) -> cb.like(cb.upper(mapper.apply(root)), likeValue);
    }

    public static <T> Specification<T> hasPropertyIgnoreCase(String propertyName, String propertyValue) {
        if (propertyValue == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.equal(cb.upper(root.get(propertyName)), propertyValue.toUpperCase(Locale.ROOT));
    }

    public static <T, U> Specification<T> hasRequiredProperty(String propertyName, U propertyValue) {
        if (propertyValue == null || "".equals(propertyValue)) {
            return disjunction();
        }
        return (root, query, cb) -> cb.equal(root.get(propertyName), propertyValue);
    }

    public static <T, U> Specification<T> hasPropertyNot(String propertyName, U propertyValue) {
        if (propertyValue == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.notEqual(root.get(propertyName), propertyValue);
    }

    public static <T, U> Specification<T> hasPropertyIn(String propertyName, Collection<U> values) {
        if (values == null) {
            return conjunction();
        }
        if (values.isEmpty()) {
            return disjunction();
        }
        return (root, query, cb) -> root.get(propertyName).in(values);
    }

    public static <T, U> Specification<T> hasPropertyIn(Collection<U> values, Function<Root<?>, Path<?>> mapper) {
        if (values == null) {
            return conjunction();
        }
        if (values.isEmpty()) {
            return disjunction();
        }
        return (root, query, cb) -> mapper.apply(root).in(values);
    }

    public static <T, X extends Comparable<X>> Specification<T> after(String propertyName, X from) {
        if (from == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.greaterThan(root.get(propertyName), from);
    }

    public static <T, X extends Comparable<X>> Specification<T> afterInclusive(String propertyName, X from) {
        if (from == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(propertyName), from);
    }

    public static <T, X extends Comparable<X>> Specification<T> before(String propertyName, X to) {
        if (to == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.lessThan(root.get(propertyName), to);
    }

    public static <T, X extends Comparable<X>> Specification<T> beforeInclusive(String propertyName, X to) {
        if (to == null) {
            return conjunction();
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(propertyName), to);
    }

    public static <T> Specification<T> hasPropertyNotNull(String propertyName) {
        return (root, query, cb) -> cb.isNotNull(root.get(propertyName));
    }

    public static <T> Specification<T> hasPropertyStartingWith(String propertyName, String propertyValue) {
        if (propertyValue == null) {
            return conjunction();
        }
        return (root, query, cb) -> likeIgnoreCase(cb, root, propertyName, propertyValue);
    }

    public static <T> Specification<T> groupBy(String... propertyName) {
        return (root, query, criteriaBuilder) -> {
            query.groupBy(Arrays.stream(propertyName).map(root::get).collect(Collectors.toList()));
            return criteriaBuilder.conjunction();
        };
    }

    public static <T> Specification<T> project(String... propertyName) {
        return (root, query, criteriaBuilder) -> {
            query.multiselect(Arrays.stream(propertyName).map(root::get).collect(Collectors.toList()));
            return criteriaBuilder.conjunction();
        };
    }

    public static <T> Specification<T> hasNonNullProperty(String propertyName) {
        return (root, query, cb) -> root.get(propertyName).isNotNull();
    }

    public static <T, U> Specification<T> hasKeyRequiredProperty(String propertyName, U propertyValue) {
        if (propertyValue == null) {
            return disjunction();
        }
        return (root, query, cb) -> cb.equal(root.get("key").get(propertyName), propertyValue);
    }

    public static <T, U> Specification<T> hasPropertyNotIn(String propertyName, Collection<U> values) {
        if (values == null) {
            return conjunction();
        }
        if (values.isEmpty()) {
            return disjunction();
        }
        return (root, query, cb) -> root.get(propertyName).in(values).not();
    }

    private static <T> Predicate likeIgnoreCase(CriteriaBuilder cb, Root<T> root, String propertyName,
            String propertyValue) {
        return cb.like(cb.upper(root.get(propertyName)), propertyValue.toUpperCase(Locale.ROOT) + "%");
    }

    public static <T, X extends Comparable<X>> Specification<T> hasPropertyMaxValue(Class<T> tClass, Class<X> clazz,
            String propertyName) {
        return (root, query, cb) -> {
            Subquery<X> subQuery = query.subquery(clazz);
            Root<T> subRoot = subQuery.from(tClass);
            Path<X> x = subRoot.get(propertyName);
            subQuery.select(cb.greatest(x));
            return cb.equal(root.get(propertyName), subQuery);
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> Specification<T> conjunction() {
        return (Specification<T>) CONJUNCTION;
    }

    @SuppressWarnings("unchecked")
    public static <T> Specification<T> disjunction() {
        return (Specification<T>) DISJUNCTION;
    }
}
