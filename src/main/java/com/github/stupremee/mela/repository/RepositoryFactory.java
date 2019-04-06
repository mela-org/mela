package com.github.stupremee.mela.repository;

import com.github.stupremee.mela.beans.Bean;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 07.04.2019
 */
@SuppressWarnings("unused")
public interface RepositoryFactory {

  /**
   * Creates a new {@link Repository}.
   *
   * @param name The name of the {@link Repository}, e.g. the table name
   * @param type The class of the type of the {@link Repository}
   * @param <T> The type of the {@link Repository}
   * @return The new {@link Repository}
   */
  <T extends Bean> Repository<T> createRepository(Class<T> type, String name);
}
