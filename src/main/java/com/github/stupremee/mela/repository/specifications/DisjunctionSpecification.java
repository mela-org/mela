package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.SnowflakeBean;
import com.github.stupremee.mela.repository.Specification;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
@SuppressWarnings("unused")
final class DisjunctionSpecification<T extends SnowflakeBean> extends CompositeSpecification<T> {

  DisjunctionSpecification(Specification<T> one,
      Specification<T> two) {
    super(List.of(one, two));
  }

  @Override
  public boolean isSatisfiedBy(@NotNull T candidate) {
    return getSpecifications().stream()
        .anyMatch(specification -> specification.isSatisfiedBy(candidate));
  }
}
