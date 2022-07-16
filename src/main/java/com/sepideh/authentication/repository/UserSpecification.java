package com.sepideh.authentication.repository;

import com.sepideh.authentication.base.SearchCriteria;
import com.sepideh.authentication.model.user.User;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification implements Specification<User> {

  private SearchCriteria criteria;

  public UserSpecification(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Specification<User> and(Specification<User> other) {
    return Specification.super.and(other);
  }

  @Override
  public Specification<User> or(Specification<User> other) {
    return Specification.super.or(other);
  }

  @Override
  public Predicate toPredicate(
      Root<User> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ) {

    if (criteria.getOperation().equalsIgnoreCase(">")) {
      return criteriaBuilder.greaterThanOrEqualTo(
          root.get(criteria.getKey()), criteria.getValue().toString()
      );
    }
    else if (criteria.getOperation().equalsIgnoreCase("<")) {
      return criteriaBuilder.lessThanOrEqualTo(
          root.get(criteria.getKey()), criteria.getValue().toString()
      );
    }
    else if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return criteriaBuilder.like(
            root.get(criteria.getKey()), "%" + criteria.getValue() + "%"
        );
      } else {
        return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
      }
    }

    return null;
  }
}
