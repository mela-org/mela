package com.github.stupremee.mela.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stupremee.mela.query.Criteria;
import com.github.stupremee.mela.query.criterias.EqualsCriteria;
import org.junit.jupiter.api.Test;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 16.06.19
 */
@SuppressWarnings("WeakerAccess")
public class HashCodeTest {

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
