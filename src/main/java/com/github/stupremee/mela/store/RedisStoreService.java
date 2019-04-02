package com.github.stupremee.mela.store;

import com.austinv11.servicer.WireService;
import discord4j.store.api.Store;
import discord4j.store.api.primitive.ForwardingStore;
import discord4j.store.api.primitive.LongObjStore;
import discord4j.store.api.service.StoreService;
import discord4j.store.api.util.StoreContext;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.RedisCodec;
import java.io.Serializable;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class RedisStoreService implements StoreService {

  private final StatefulRedisConnection<String, Object> connection;
  private final RedisClient client;
  private final String keyPrefix;

  RedisStoreService(RedisClient client, RedisCodec<String, Object> codec, String keyPrefix) {
    this.client = client;
    this.connection = client.connect(codec);
    this.keyPrefix = keyPrefix;
  }

  @Override
  public boolean hasGenericStores() {
    return true;
  }

  @Override
  public <K extends Comparable<K>, V extends Serializable> Store<K, V> provideGenericStore(
      Class<K> keyClass, Class<V> valueClass) {
    return new RedisStore<>(connection, keyPrefix + valueClass.getSimpleName());
  }

  @Override
  public boolean hasLongObjStores() {
    return true;
  }

  @Override
  public <V extends Serializable> LongObjStore<V> provideLongObjStore(Class<V> valueClass) {
    return new ForwardingStore<>(provideGenericStore(Long.class, valueClass));
  }

  @Override
  public void init(StoreContext context) {
  }

  @Override
  public Mono<Void> dispose() {
    return Mono.defer(() -> Mono.fromFuture(client.shutdownAsync()));
  }
}
