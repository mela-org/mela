package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.Specification;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
@SuppressWarnings("unused")
abstract class CompositeSpecification<T extends Bean> implements Specification<T> {

  private final List<Specification<T>> specifications;

  CompositeSpecification(List<Specification<T>> specifications) {
    this.specifications = specifications;
  }

  /**
   * Returns the inner specifications.
   *
   * @return The inner specifications
   */
  List<Specification<T>> getSpecifications() {
    return specifications;
  }

  Set<Specification<T>> getUnsatisfiedForCandidate(T candidate) {
    return specifications.stream()
        .filter(s -> !s.isSatisfiedBy(candidate))
        .collect(Collectors.toUnmodifiableSet());
  }
}
