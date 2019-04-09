package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.SnowflakeBean;
import com.github.stupremee.mela.repository.Specification;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
final class PredicateSpecification<T extends SnowflakeBean> implements Specification<T> {

  private final Predicate<T> predicate;

  PredicateSpecification(Predicate<T> predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean isSatisfiedBy(@NotNull T candidate) {
    return predicate.test(candidate);
  }

  @NotNull
  @Override
  public Predicate<T> toPredicate() {
    return predicate;
  }
}
