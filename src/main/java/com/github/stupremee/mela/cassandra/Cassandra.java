package com.github.stupremee.mela.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 24.03.2019
 */
@SuppressWarnings("unused")
public interface Cassandra {

  static Cassandra vanilla(CassandraCredentials credentials) {
    return new VanillaCassandra(credentials);
  }

  /**
   * Connects asynchronously to the database with the given credentials.
   *
   * @return The {@link Cassandra} in a {@link Mono}
   * @throws IllegalStateException if the Database is already connected
   */
  Mono<Cassandra> connectAsync();

  /**
   * Connects synchronously to the database with the given credentials.
   *
   * @return The {@link Session}
   * @throws IllegalStateException if the Database is already connected
   */
  Session connect();

  /**
   * Executes a CQL Statement synchronously and returns the result set.
   *
   * @param statement The CQL Statement as {@link Statement}
   * @return The Result set
   * @throws IllegalStateException if the Database is not connected
   */
  ResultSet execute(Statement statement);

  /**
   * Executes a CQL Statement asynchronously and returns the result set as a {@link
   * ResultSetFuture}.
   *
   * @param statement The CQL Statement as {@link Statement}
   * @return The Result set as a {@link ResultSetFuture}
   * @throws IllegalStateException if the Database is not connected
   */
  Mono<ResultSet> executeAsync(Statement statement);

  /**
   * Returns the getCluster from the cassandra database.
   *
   * @return The {@link Cluster}
   */
  @NotNull
  Cluster getCluster();

  /**
   * Returns the getSession.
   *
   * @return The {@link Session}
   * @throws IllegalStateException if the Database is not connected
   */
  Session getSession();

  /**
   * Returns the mapping manager to getInstance accessors, {@link Mapper Mappers} etc.
   *
   * @return The {@link MappingManager}
   * @throws IllegalStateException if the Database is not connected
   */
  MappingManager getMappingManager();

  /**
   * Creates a new {@link Mapper} from the clazz that is given in the parameter.
   *
   * @param clazz The {@link Class} that the {@link Mapper} should map
   * @return The {@link Mapper}
   * @throws IllegalStateException if the Database is not connected
   */
  <T> Mapper<T> getMapper(@NotNull Class<T> clazz);

  /**
   * Creates a getAccessor via the {@link MappingManager}
   *
   * @param clazz The getAccessor class
   * @param <T> The type of the class
   * @return The getAccessor
   * @throws IllegalStateException if the Database is not connected
   */
  <T> T getAccessor(@NotNull Class<T> clazz);

  /**
   * Returns the {@link CodecRegistry} that the getCluster will use to convert data types.
   *
   * @return The {@link CodecRegistry}
   */
  @NotNull
  CodecRegistry getCodecRegistry();

  /**
   * Returns the namespace / keyspace that should be used to retrieve data from the database.
   *
   * @return The keyspace name
   */
  @NotNull
  String getKeyspace();
}
