package org.codelogger.core.repository;

import static org.springframework.data.jpa.domain.Specifications.where;

import net.ftng.commons.lang.utils.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GenericRepositorySpecification {

  private static final String DECOLLATOR = "\\.";

  public static <T> Specification<T> withEqual(final String fieldPath, final Object fieldValue) {

    return new Specification<T>() {

      @Override
      public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
          final CriteriaBuilder builder) {

        String[] sequencePaths = fieldPath.split(DECOLLATOR);
        Path<Object> path = null;
        for (String sequencePath : sequencePaths) {

          path = path == null ? root.get(sequencePath) : path.get(sequencePath);
        }
        return builder.equal(path, fieldValue);
      }
    };
  }

  public static <T, V extends Comparable<? super V>> Specification<T> withGreaterThan(
      final String fieldPath, final V fieldValue) {

    return new Specification<T>() {

      @SuppressWarnings("unchecked")
      @Override
      public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
          final CriteriaBuilder cb) {

        String[] sequencePaths = fieldPath.split(DECOLLATOR);
        Path<Object> path = null;
        for (String sequencePath : sequencePaths) {

          path = path == null ? root.get(sequencePath) : path.get(sequencePath);
        }
        return cb.greaterThan(path.as(fieldValue.getClass()), fieldValue);
      }
    };
  }

  public static <T> Specification<T> with(final String fieldPath, final Collection<?> fieldValues) {

    return new Specification<T>() {

      @Override
      public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
          final CriteriaBuilder builder) {

        String[] sequencePaths = fieldPath.split(DECOLLATOR);
        Path<Object> path = null;
        for (int i = 0; i < sequencePaths.length - 1; i++) {

          path = path == null ? root.get(sequencePaths[i]) : path.get(sequencePaths[i]);
        }
        Expression<Set<String>> jobStatus = root.get(sequencePaths[sequencePaths.length - 1]);
        return builder.isMember(CollectionUtils.join(fieldValues, ","), jobStatus);
      }
    };
  }

  /**
   * Simple static factory method to add some syntactic sugar around given
   * {@link Specification}s with ANDs after WHERE.
   *
   * @param specifications can be null or empty.
   * @return null if given specifications is null or empty, otherwise return
   * sugar aroud {@link Specification}.
   */
  public static <T> Specifications<T> toWhereSpecificationsJoinWithAnd(
      final List<Specification<T>> specifications) {

    if (CollectionUtils.isEmpty(specifications)) {
      return null;
    } else {
      Specifications<T> specs = where(specifications.remove(0));
      for (Specification<T> s : specifications) {
        specs = specs.and(s);
      }
      return specs;
    }
  }
}
