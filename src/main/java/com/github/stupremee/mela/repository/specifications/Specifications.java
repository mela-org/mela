package com.github.stupremee.mela.repository.specifications;

import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.Specification;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

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
  @NotNull
  public static <T extends Bean> Specification<T> or(Specification<T> left,
      Specification<T> right) {
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
  @NotNull
  public static <T extends Bean> Specification<T> and(Specification<T> left,
      Specification<T> right) {
    return new ConjunctionSpecification<>(left, right);
  }

  /**
   * Returns a negation specification composed from the given specification.
   *
   * @return The negation specification.
   */
  @NotNull
  public static <T extends Bean> Specification<T> not(Specification<T> specification) {
    return new NegationSpecification<>(specification);
  }

  /**
   * Returns a {@link Specification} that is always false.
   *
   * @param <T> The type of the {@link Specification}
   * @return The {@link Specification} that is always false
   */
  @NotNull
  public static <T extends Bean> Specification<T> alwaysFalse() {
    return AlwaysFalseSpecification.getInstance();
  }

  /**
   * Returns a {@link Specification} that is always true.
   *
   * @param <T> The type of the {@link Specification}
   * @return The {@link Specification} that is always true
   */
  @NotNull
  public static <T extends Bean> Specification<T> alwaysTrue() {
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
  @NotNull
  public static <T extends Bean> Specification<T> ofPredicate(Predicate<T> predicate) {
    return new PredicateSpecification<>(predicate);
  }
}
