package com.github.stupremee.mela.store;

import discord4j.core.DiscordClientBuilder;
import discord4j.store.api.Store;
import discord4j.store.api.util.LongLongTuple2;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class RedisStore<K extends Comparable<K>, V extends Serializable> implements Store<K, V> {

  private final RedisReactiveCommands<String, Object> commands;
  private final String store;

  RedisStore(StatefulRedisConnection<String, Object> connection, String store) {
    this.store = store;
    this.commands = connection.reactive();
  }

  @Override
  public Mono<Void> save(K key, V value) {
    System.out.println(key);
    return Mono.defer(() -> commands.hset(store, generateKey(key), value).then());
  }

  @Override
  public Mono<Void> save(Publisher<Tuple2<K, V>> entryStream) {
    return Mono.from(entryStream)
        .flatMap(t -> commands.hset(store, generateKey(t.getT1()), t.getT2()).then());
  }

  @Override
  public Mono<V> find(K id) {
    return Mono.defer(() -> commands.hget(store, generateKey(id)).map(this::cast));
  }

  @Override
  @SuppressWarnings("unchecked")
  public Flux<V> findInRange(K start, K end) {
    return Mono.defer(() -> commands.hgetall(store))
        .flatMapIterable(Map::entrySet)
        .filter(entry -> findInRangePredicate(start, end).test((K) entry.getKey()))
        .map(Map.Entry::getValue)
        .map(this::cast);
  }

  @Override
  public Mono<Long> count() {
    return Mono.defer(() -> commands.hlen(store));
  }

  @Override
  public Mono<Void> delete(K id) {
    return Mono.defer(() -> commands.hdel(store, generateKey(id)).then());
  }

  @Override
  public Mono<Void> delete(Publisher<K> ids) {
    return Mono.defer(() -> Flux.from(ids).flatMap(this::delete).then());
  }

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Void> deleteInRange(K start, K end) {
    return Flux.defer(() -> commands.hkeys(store))
        .filter(key -> findInRangePredicate(start, end).test((K) key))
        .flatMap(key -> Mono.defer(() -> commands.hdel(store, key).then()))
        .then();

  }

  @Override
  public Mono<Void> deleteAll() {
    return Mono.defer(() -> commands.del(store).then());
  }

  @Override
  public Flux<K> keys() {
    return Flux.defer(() -> commands.hkeys(store)).map(this::cast);
  }

  @Override
  public Flux<V> values() {
    return Flux.defer(() -> commands.hvals(store)).map(this::cast);
  }

  @Override
  public Mono<Void> invalidate() {
    return deleteAll();
  }

  private String generateKey(K key) {
    if (key instanceof LongLongTuple2) {
      var tuple = (LongLongTuple2) key;
      return tuple.getT1() + "-" + tuple.getT2();
    }
    return key.toString();
  }

  @SuppressWarnings("unchecked")
  private <V> V cast(Object o) {
    return (V) o;
  }

  private Predicate<K> findInRangePredicate(K start, K end) {
    return k -> start.compareTo(k) <= 0 && end.compareTo(k) > 0;
  }
}
