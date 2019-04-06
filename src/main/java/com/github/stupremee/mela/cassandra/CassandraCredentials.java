package com.github.stupremee.mela.cassandra;

import com.datastax.driver.core.AuthProvider;
import com.github.stupremee.mela.annotations.Bean;
import java.net.InetAddress;
import java.util.Collection;
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

  Collection<InetAddress> getContactPoints();

  String getKeyspace();

  AuthProvider getAuthProvider();
}
