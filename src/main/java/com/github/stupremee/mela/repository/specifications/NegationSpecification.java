package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.Specification;
import java.util.Collections;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
final class NegationSpecification<T extends Bean> extends CompositeSpecification<T> {

  NegationSpecification(Specification<T> specification) {
    super(Collections.singletonList(specification));
  }

  @Override
  public boolean isSatisfiedBy(T candidate) {
    return getSpecifications().get(0).isSatisfiedBy(candidate);
  }
}
