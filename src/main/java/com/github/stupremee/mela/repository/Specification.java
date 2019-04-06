package com.github.stupremee.mela.repository;

import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.specifications.Specifications;
import java.util.function.Predicate;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public interface Specification<T extends Bean> {

  /**
   * Check if the {@code candidate} is satisfied by the specifications.
   *
   * @param candidate an object to test
   * @return {@code true} if the candidate satisfies the specifications otherwise {@code false}
   */
  boolean isSatisfiedBy(T candidate);

  /**
   * Returns the new disjunction specifications composed from this and other specifications.
   *
   * @param other The right-hand side specifications.
   * @return The new disjunction specifications.
   */
  default Specification<T> or(Specification<T> other) {
    return Specifications.or(this, other);
  }

  /**
   * Returns the new conjunction specifications composed from this and other specifications.
   *
   * @param other The right-hand side specifications
   * @return The new conjunction specifications
   */
  default Specification<T> and(Specification<T> other) {
    return Specifications.and(this, other);
  }

  /**
   * Returns the new negation specifications composed from this specifications.
   *
   * @return The new negation specifications.
   */
  default Specification<T> not() {
    return Specifications.not(this);
  }

  /**
   * Returns this specifications as predicate.
   *
   * @return The specifications as predicate.
   */
  default Predicate<T> toPredicate() {
    return this::isSatisfiedBy;
  }
}
