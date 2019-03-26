package com.github.stupremee.mela.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 24.03.2019
 */
@SuppressWarnings("unused")
public interface Cassandra {

  /**
   * Executes a CQL Statement synchronously and returns the result set.
   *
   * @param statement The CQL Statement as {@link PreparedStatement}
   * @return The Result set
   */
  ResultSet execute(PreparedStatement statement);

  /**
   * Executes a CQL Statement asynchronously and returns the result set in a {@link Mono}.
   *
   * @param statement The CQL Statement as {@link PreparedStatement}
   * @return The Result set in a {@link Mono}
   */
  Mono<ResultSet> executeAsync(PreparedStatement statement);

  /**
   * Returns the cluster from the cassandra database.
   *
   * @return The {@link Cluster}
   */
  Cluster cluster();

  /**
   * Returns the session.
   *
   * @return The {@link Session}
   */
  Session session();

  /**
   * Returns the mapping manager to create accessors, {@link ObjectMapper ObjectMappers} etc.
   *
   * @return The {@link MappingManager}
   */
  MappingManager mapper();

  /**
   * Creates a new {@link ObjectMapper} from the clazz that is given in the parameter.
   *
   * @param clazz The {@link Class} that the {@link ObjectMapper} should map
   * @return The {@link ObjectMapper}
   */
  ObjectMapper mapper(Class<?> clazz);

  /**
   * Creates a accessor via the {@link MappingManager}
   *
   * @param clazz The accessor class
   * @param <T> The type of the class
   * @return The accessor
   */
  <T> T accessor(Class<T> clazz);
}
