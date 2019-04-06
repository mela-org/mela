package com.github.stupremee.mela.cassandra;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

import com.datastax.driver.mapping.Mapper;
import com.github.stupremee.mela.beans.Bean;
import com.github.stupremee.mela.repository.Repository;
import com.github.stupremee.mela.repository.Specification;
import com.google.common.util.concurrent.ListenableFuture;
import discord4j.core.object.util.Snowflake;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 07.04.2019
 */
final class CassandraRepository<T extends Bean> implements Repository<T> {

  //private static final Logger LOG = Loggers.getLogger("CassandraRepository");
  private final String name;
  private final Mapper<T> mapper;

  CassandraRepository(String name, Class<T> type, Cassandra cassandra) {
    this.name = name;
    this.mapper = cassandra.getMapper(type);
  }

  @NotNull
  @Override
  public Flux<T> query(@NotNull Specification<T> query) {
    return getAll()
        .filter(query::isSatisfiedBy);
  }

  @NotNull
  @Override
  public Mono<T> queryFirst(@NotNull Specification<T> query) {
    return query(query)
        .singleOrEmpty();
  }

  @NotNull
  @Override
  public Mono<T> findById(@NotNull Snowflake id) {
    return Mono.defer(() -> monoFromFuture(mapper.getAsync(id.asLong())));
  }

  @NotNull
  @Override
  public Flux<T> findAllById(@NotNull Iterable<Snowflake> ids) {
    return Flux.fromIterable(ids)
        .flatMap(this::findById);
  }

  @NotNull
  @Override
  public Flux<T> getAll() {
    return Flux.defer(() -> monoFromFuture(
        mapper.mapAsync(mapper.getManager().getSession().executeAsync("SELECT * FROM " + name))))
        .flatMap(result -> Flux.fromIterable(result.all()));
  }

  @NotNull
  @Override
  public Mono<Void> deleteById(@NotNull Snowflake id) {
    return deferMonoFromFuture(mapper.deleteAsync(id.asLong()));
  }

  @NotNull
  @Override
  public Mono<Void> deleteAll(@NotNull Iterable<T> beans) {
    return Flux.fromIterable(beans)
        .flatMap(this::delete)
        .then();
  }

  @NotNull
  @Override
  public Mono<Void> deleteAll() {
    return deferMonoFromFuture(mapper.deleteAsync());
  }

  @NotNull
  @Override
  public Mono<Void> save(@NotNull T bean) {
    return deferMonoFromFuture(mapper.saveAsync(bean));
  }

  @NotNull
  @Override
  public Mono<Void> saveMany(@NotNull Iterable<T> beans) {
    return Flux.fromIterable(beans)
        .flatMap(this::save)
        .then();
  }

  @NotNull
  @Override
  public Mono<Void> update(@NotNull T bean) {
    return deferMonoFromFuture(mapper.saveAsync(bean));
  }

  @NotNull
  @Override
  public Mono<Void> updateMany(@NotNull Iterable<T> beans) {
    return Flux.fromIterable(beans)
        .flatMap(this::update)
        .then();
  }

  @NotNull
  @Override
  public Mono<Boolean> exists(@NotNull Snowflake id) {
    return findById(id)
        .map(Objects::nonNull);
  }

  @NotNull
  @Override
  public Mono<Long> count() {
    return monoFromFuture(
        mapper.getManager().getSession().executeAsync("SELECT COUNT(*) FROM " + name))
        .map(r -> r.one().getLong(0));
  }

  private Mono<Void> deferMonoFromFuture(ListenableFuture<?> future) {
    return Mono.defer(() -> Mono.fromFuture(toCompletableFuture(future)).then());
  }

  private <T> Mono<T> monoFromFuture(ListenableFuture<T> future) {
    return Mono.fromFuture(toCompletableFuture(future));
  }
}
