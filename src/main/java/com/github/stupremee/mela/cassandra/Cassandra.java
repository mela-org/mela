package com.github.stupremee.mela.cassandra;

import com.datastax.driver.core.Cluster;
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

  ResultSet execute(String statement);

  Mono<ResultSet> executeAsync(String statement);

  Cluster cluster();

  Session session();

  MappingManager mapper();

  ObjectMapper mapper(Class<?> clazz);

  <T> T accessor(Class<T> clazz);
}
