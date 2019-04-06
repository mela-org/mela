package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.Specification;
import java.util.List;
import java.util.function.Predicate;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
@SuppressWarnings("unused")
final class DisjunctionSpecification<T extends Bean> extends CompositeSpecification<T> {

  DisjunctionSpecification(Specification<T> one,
      Specification<T> two) {
    super(List.of(one, two));
  }

  @Override
  public boolean isSatisfiedBy(T candidate) {
    return getSpecifications().stream()
        .anyMatch(specification -> specification.isSatisfiedBy(candidate));
  }

  @Override
  public Specification<T> and(Specification<T> other) {
    return null;
  }

  @Override
  public Specification<T> not() {
    return null;
  }

  @Override
  public Predicate<T> toPredicate() {
    return null;
  }
}
