package com.github.stupremee.mela.cassandra;

import com.datastax.driver.core.AuthProvider;
import com.github.stupremee.mela.annotations.Bean;
import com.github.stupremee.mela.cassandra.ImmutableCassandraCredentials.Builder;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import org.immutables.value.Value.Immutable;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
@Immutable
@Bean
@SuppressWarnings("unused")
public interface CassandraCredentials {

  /**
   * Creates a new builder to build a {@link CassandraCredentials} instance.
   *
   * @return The builder
   */
  static Builder builder() {
    return ImmutableCassandraCredentials.builder();
  }

  /**
   * Returns all contact points that the cassandra cluster have.
   *
   * @return A {@link Collection} containing all the contact points
   */
  List<InetAddress> getContactPoints();

  /**
   * Returns the keyspace.
   *
   * @return The keyspace
   */
  String getKeyspace();

  /**
   * Returns the auth provider that should be used to connect to the database.
   *
   * @return The {@link AuthProvider}
   */
  AuthProvider getAuthProvider();
}
