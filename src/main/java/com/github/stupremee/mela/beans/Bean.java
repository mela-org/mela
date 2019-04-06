package com.github.stupremee.mela.beans;

import java.io.Serializable;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
public abstract class Bean implements Serializable {

  private static final long serialVersionUID = -1945442693247089196L;

  protected long id;

  public Bean() {
  }

  public Bean(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
