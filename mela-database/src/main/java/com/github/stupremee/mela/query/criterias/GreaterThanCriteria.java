package com.github.stupremee.mela.query.criterias;

import com.github.stupremee.mela.query.Criteria;
import com.github.stupremee.mela.query.CriteriaVisitor;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.06.19
 */
public final class GreaterThanCriteria<T extends Comparable<T>> implements Criteria {

  private final String key;
  private final T lowerBound;


  private GreaterThanCriteria(String key, T lowerBound) {
    this.key = key;
    this.lowerBound = lowerBound;
  }

  /**
   * Returns the lowerBound that this criteria should match.
   *
   * @return The lowerBound as an {@link Object}
   */
  @Nonnull
  public T getLowerBound() {
    return lowerBound;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void accept(CriteriaVisitor visitor) {
    visitor.visitGreaterThan(this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key", key)
        .add("lowerBound", lowerBound)
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

    if (!(o instanceof GreaterThanCriteria)) {
      return false;
    }

    var other = (GreaterThanCriteria) o;
    return Objects.equal(this.key, other.key)
        && Objects.equal(this.lowerBound, other.lowerBound);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key, lowerBound);
  }

  /**
   * Static factory method to create a new {@link GreaterThanCriteria}.
   */
  public static <T extends Comparable<T>> Criteria create(String key, T value) {
    Preconditions.checkNotNull(key, "key can't be null.");
    Preconditions.checkNotNull(value, "lowerBound can't be null.");
    return new GreaterThanCriteria<>(key, value);
  }
}
