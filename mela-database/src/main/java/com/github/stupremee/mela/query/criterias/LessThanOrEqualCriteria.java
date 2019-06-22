package com.github.stupremee.mela.query.criterias;

import com.github.stupremee.mela.query.Criteria;
import com.github.stupremee.mela.query.CriteriaVisitor;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

/**
 * @author Stu <https://github.com/Stupremee>
 * @since 10.06.19
 */
public final class LessThanOrEqualCriteria<T extends Comparable<T>> implements Criteria {

  private final String key;
  private final T upperBound;


  private LessThanOrEqualCriteria(String key, T upperBound) {
    this.key = key;
    this.upperBound = upperBound;
  }

  /**
   * Returns the upperBound that this criteria should match.
   *
   * @return The upperBound as an {@link Object}
   */
  @Nonnull
  public T getNumber() {
    return upperBound;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void accept(CriteriaVisitor visitor) {
    visitor.visitLessThanOrEqual(this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key", key)
        .add("upperBound", upperBound)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (o == this) {
      return true;
    }

    if (!(o instanceof LessThanOrEqualCriteria)) {
      return false;
    }

    var other = (LessThanOrEqualCriteria) o;
    return Objects.equal(this.key, other.key)
        && Objects.equal(this.upperBound, other.upperBound);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key, upperBound);
  }

  /**
   * Static factory method to create a new {@link LessThanOrEqualCriteria}.
   */
  public static <T extends Comparable<T>> Criteria create(String key, T value) {
    Preconditions.checkNotNull(key, "key can't be null.");
    Preconditions.checkNotNull(value, "upperBound can't be null.");
    return new LessThanOrEqualCriteria<>(key, value);
  }

}
