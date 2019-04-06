package com.github.stupremee.mela.cassandra;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.Repository;
import com.github.stupremee.mela.util.Holder;
import com.github.stupremee.mela.util.Loggers;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 26.03.2019
 */
@SuppressWarnings({"unused"})
public class VanillaCassandra implements Cassandra {

  private static final Logger LOGGER = Loggers.getLogger("Cassandra");
  private final Cluster cluster;
  private final CodecRegistry codecRegistry;
  private final String keyspace;
  private final Holder<MappingManager> mappingManager;
  private final Holder<Session> session;

  VanillaCassandra(CassandraCredentials credentials) {
    Preconditions.checkNotNull(credentials, "credentials can't be null");
    this.mappingManager = Holder.empty();
    this.session = Holder.empty();
    this.codecRegistry = new CodecRegistry();
    this.keyspace = credentials.getKeyspace();
    this.cluster = Cluster.builder()
        .addContactPoints(credentials.getContactPoints())
        .withAuthProvider(credentials.getAuthProvider())
        .build();
  }

  @Override
  public Mono<Cassandra> connectAsync() {
    var session = getOrThrow(this.session);
    if (session != null && !session.isClosed()) {
      throw new IllegalStateException("Already connected to a database!");
    }

    return Mono.fromFuture(toCompletableFuture(cluster.connectAsync(keyspace)))
        .doOnSuccess(this.session::setValue)
        .doOnSuccess(s -> mappingManager.setValue(new MappingManager(s)))
        .doOnError(t -> LOGGER
            .error("Failed to connect to database.", t))
        .doOnSuccess(s -> LOGGER
            .info("Database successfully connected to {}", s.getCluster().getClusterName()))
        .map(s -> this);
  }

  @Override
  public Session connect() {
    return connectAsync()
        .map(Cassandra::getSession)
        .block();
  }

  @Override
  public ResultSet execute(Statement statement) {
    Preconditions.checkNotNull(statement, "statement can't be null");
    checkConnection();
    return getOrThrow(this.session).execute(statement);
  }

  @Override
  public Mono<ResultSet> executeAsync(Statement statement) {
    Preconditions.checkNotNull(statement, "statement can't be null");
    checkConnection();
    return Mono.defer(() ->
        Mono.fromFuture(
            toCompletableFuture(
                getOrThrow(this.session).executeAsync(statement))));
  }

  @NotNull
  @Override
  public Cluster getCluster() {
    checkConnection();
    return cluster;
  }

  @Override
  public Session getSession() {
    checkConnection();
    return getOrThrow(this.session);
  }

  @Override
  public MappingManager getMappingManager() {
    checkConnection();
    return getOrThrow(mappingManager);
  }

  @Override
  public <T> Mapper<T> getMapper(@NotNull Class<T> clazz) {
    Preconditions.checkNotNull(clazz, "clazz can't be null");
    checkConnection();
    return getOrThrow(mappingManager).mapper(clazz, keyspace);
  }

  @Override
  public <T> T getAccessor(@NotNull Class<T> clazz) {
    checkConnection();
    return getOrThrow(mappingManager).createAccessor(Preconditions.checkNotNull(clazz));
  }

  @NotNull
  @Override
  public CodecRegistry getCodecRegistry() {
    return codecRegistry;
  }

  @Override
  public @NotNull String getKeyspace() {
    return keyspace;
  }

  private <T> T getOrThrow(Holder<T> optional) {
    return optional.toJavaOptional().orElseThrow(
        () -> new IllegalStateException("Database is not connected or something went wrong."));
  }

  private void checkConnection() {
    var session = getOrThrow(this.session);
    if (session == null || session.isClosed()) {
      throw new IllegalStateException(
          "Database is not connected! "
              + "Please connect before doing actions that needs a connection.");
    }
  }

  @Override
  public <T extends Bean> Repository<T> createRepository(Class<T> type, String name) {
    return new CassandraRepository<>(name, type, this);
  }
}
