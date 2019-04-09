package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.SnowflakeBean;
import com.github.stupremee.mela.repository.Specification;
import com.google.common.base.Preconditions;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public final class Specifications {

  private Specifications() {
  }

  /**
   * Returns the new disjunction specifications composed from {@code left} and {@code right}.
   *
   * @param left The left-handed {@link Specification}
   * @param right The right-handed {@link Specification}
   * @param <T> The type of the {@link Specification}
   * @return The {@link Specification}
   */
  @Nonnull
  public static <T extends SnowflakeBean> Specification<T> or(@Nonnull Specification<T> left,
      @Nonnull Specification<T> right) {
    Preconditions.checkNotNull(left, "left can't be null.");
    Preconditions.checkNotNull(right, "right can't be null.");
    return new DisjunctionSpecification<>(left, right);
  }

  /**
   * Returns the new conjunction specifications composed from {@code left} and {@code right}.
   *
   * @param left The left-handed {@link Specification}
   * @param right The right-handed {@link Specification}
   * @param <T> The type of the {@link Specification}
   * @return The {@link Specification}
   */
  @Nonnull
  public static <T extends SnowflakeBean> Specification<T> and(@Nonnull Specification<T> left,
      @Nonnull Specification<T> right) {
    Preconditions.checkNotNull(left, "left can't be null.");
    Preconditions.checkNotNull(right, "right can't be null.");
    return new ConjunctionSpecification<>(left, right);
  }

  /**
   * Returns a negation specification composed from the given specification.
   *
   * @return The negation specification.
   */
  @Nonnull
  public static <T extends SnowflakeBean> Specification<T> not(
      @Nonnull Specification<T> specification) {
    Preconditions.checkNotNull(specification, "specification can't be null.");
    return new NegationSpecification<>(specification);
  }

  /**
   * Returns a {@link Specification} that is always false.
   *
   * @param <T> The type of the {@link Specification}
   * @return The {@link Specification} that is always false
   */
  @Nonnull
  public static <T extends SnowflakeBean> Specification<T> alwaysFalse() {
    return AlwaysFalseSpecification.getInstance();
  }

  /**
   * Returns a {@link Specification} that is always true.
   *
   * @param <T> The type of the {@link Specification}
   * @return The {@link Specification} that is always true
   */
  @Nonnull
  public static <T extends SnowflakeBean> Specification<T> alwaysTrue() {
    return AlwaysTrueSpecification.getInstance();
  }

  /**
   * Creates a new {@link Specification} that is satisfied by all objects that will be accepted by
   * the {@code predicate}.
   *
   * @param predicate The predicate
   * @param <T> The type of the predicate and the {@link Specification}
   * @return The new {@link Specification}
   */
  @Nonnull
  public static <T extends SnowflakeBean> Specification<T> ofPredicate(
      @Nonnull Predicate<T> predicate) {
    Preconditions.checkNotNull(predicate, "predicate can't be null.");
    return new PredicateSpecification<>(predicate);
  }
}
