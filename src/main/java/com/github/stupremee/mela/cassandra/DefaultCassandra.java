package com.github.stupremee.mela.cassandra;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.github.stupremee.mela.util.Loggers;
import java.net.InetAddress;
import java.util.Collection;
import net.javacrumbs.futureconverter.java8guava.FutureConverter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 26.03.2019
 */
@SuppressWarnings("unused")
public class DefaultCassandra implements Cassandra {

  private static final Logger log = Loggers.logger("Cassandra");
  private final Cluster cluster;
  private final CodecRegistry codecRegistry;
  private final String keyspace;
  private boolean connected;
  private Session session;
  private MappingManager mappingManager;

  DefaultCassandra(Collection<InetAddress> addresses, AuthProvider authProvider, String keyspace) {
    this.codecRegistry = new CodecRegistry();
    this.keyspace = keyspace;
    this.cluster = Cluster.builder()
        .addContactPoints(addresses)
        .withAuthProvider(authProvider)
        .build();
  }

  @Override
  public Mono<Session> connectAsync() {
    if (connected) {
      throw new IllegalStateException("Already connected to a database!");
    }

    return Mono.fromFuture(FutureConverter.toCompletableFuture(cluster.connectAsync(keyspace)))
        .doOnSuccess(s -> connected = true)
        .doOnSuccess(s -> session = s)
        .doOnSuccess(s -> mappingManager = new MappingManager(s))
        .doOnSuccess(s -> log
            .info("Cassandra successfully connected to {}", s.getCluster().getClusterName()));
  }

  @Override
  public Session connect() {
    return connectAsync().block();
  }

  @Override
  public ResultSet execute(Statement statement) {
    checkConnection();
    return session.execute(statement);
  }

  @Override
  public ResultSetFuture executeAsync(Statement statement) {
    checkConnection();
    return session.executeAsync(statement);
  }

  @NotNull
  @Override
  public Cluster cluster() {
    checkConnection();
    return cluster;
  }

  @Override
  public Session session() {
    checkConnection();
    return session;
  }

  @Override
  public MappingManager mapper() {
    checkConnection();
    return mappingManager;
  }

  @Override
  public <T> Mapper<T> mapper(Class<T> clazz) {
    checkConnection();
    return mappingManager.mapper(clazz, keyspace);
  }

  @Override
  public <T> T accessor(Class<T> clazz) {
    checkConnection();
    return mappingManager.createAccessor(clazz);
  }

  @NotNull
  @Override
  public CodecRegistry codecRegistry() {
    return codecRegistry;
  }

  private void checkConnection() {
    if (session == null || session.isClosed()) {
      throw new IllegalStateException(
          "Database is not connected! "
              + "Please connect before doing actions that needs a connection.");
    }
  }
}
