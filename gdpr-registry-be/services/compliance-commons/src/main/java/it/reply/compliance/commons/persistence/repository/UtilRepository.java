package it.reply.compliance.commons.persistence.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class UtilRepository {

    private final EntityManager entityManager;

    public UtilRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T, ID> Optional<ID> findIdBy(Class<T> entityClass, Class<ID> idClass, Specification<T> specification) {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ID> query = criteriaBuilder.createQuery(idClass);
        Root<T> root = query.from(entityClass);
        query.select(root.get("id"));
        query.where(specification.toPredicate(root, query, criteriaBuilder));
        Query<ID> orderQuery = session.createQuery(query);
        return Optional.ofNullable(getSingleResultSafe(orderQuery));
    }

    public <T, ID> List<ID> findAllIdBy(Class<T> entityClass, Class<ID> idClass, Specification<T> specification,
            Pageable pageable) {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ID> criteriaQuery = criteriaBuilder.createQuery(idClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root.get("id"));
        criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        criteriaQuery.orderBy(map(pageable.getSort(), criteriaBuilder, root).collect(Collectors.toList()));
        Query<ID> query = session.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    private <T> T getSingleResultSafe(Query<T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private static final Map<Sort.Direction, BiFunction<Expression<?>, CriteriaBuilder, Order>> DIRECTION_MAPPING =
            Map.of(
            Sort.Direction.ASC, (ex, cb) -> cb.asc(ex), Sort.Direction.DESC, (ex, cb) -> cb.desc(ex));

    private <T> Stream<Order> map(Sort sort, CriteriaBuilder criteriaBuilder, Root<T> root) {
        return sort.get().map(order -> {
            String property = order.getProperty();
            Sort.Direction direction = order.getDirection();
            return DIRECTION_MAPPING.get(direction).apply(root.get(property), criteriaBuilder);
        });
    }
}
