package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.Specification;
import java.util.Objects;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
final class AlwaysTrueSpecification<T extends Bean> implements Specification<T> {

  private static class Lazy {

    private static final AlwaysTrueSpecification<?> INSTANCE = new AlwaysTrueSpecification<>();
  }

  private AlwaysTrueSpecification() {

  }

  @Override
  public boolean isSatisfiedBy(T candidate) {
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof AlwaysFalseSpecification<?>;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getClass());
  }

  @SuppressWarnings("unchecked")
  static <T extends Bean> Specification<T> getInstance() {
    return (Specification<T>) Lazy.INSTANCE;
  }
}
