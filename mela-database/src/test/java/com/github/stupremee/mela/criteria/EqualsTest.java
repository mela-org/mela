package com.github.stupremee.mela.criteria;

import static org.assertj.core.api.Assertions.*;

import com.github.stupremee.mela.query.Criteria;
import com.github.stupremee.mela.query.criterias.EqualsCriteria;
import org.junit.jupiter.api.Test;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 16.06.19
 */
@SuppressWarnings("WeakerAccess")
public class EqualsTest {

  @Test
  public void twoCriteriaShouldMatch() {
    Criteria one = EqualsCriteria.create("something", 1337);
    Criteria two = EqualsCriteria.create("something", 1337);

    assertThat(one).isEqualTo(two);
  }

  @Test
  public void twoCriteriaShouldNotMatch() {
    Criteria one = EqualsCriteria.create("something", 1337);
    Criteria two = EqualsCriteria.create("something", 1337L);

    assertThat(one).isNotEqualTo(two);
  }

  @Test
  public void twoCriteriaMatchHashCode() {
    Criteria one = EqualsCriteria.create("something", 1337L);
    Criteria two = EqualsCriteria.create("something", 1337);

    assertThat(one.hashCode()).isEqualTo(two.hashCode());
  }

  @Test
  public void twoCriteriaNotMatchHashCode() {
    Criteria one = EqualsCriteria.create("somethingg", 1337);
    Criteria two = EqualsCriteria.create("something", 1337);

    assertThat(one.hashCode()).isNotEqualTo(two.hashCode());
  }
}