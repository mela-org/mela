package com.github.stupremee.mela.store;

import com.github.stupremee.mela.store.serializer.RedisSerializer;
import io.lettuce.core.codec.RedisCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 02.04.2019
 */
public class DefaultRedisCodec<K, V> implements RedisCodec<K, V> {

  private final RedisSerializer<K> keySerializer;
  private final RedisSerializer<V> valueSerializer;

  private DefaultRedisCodec(
      RedisSerializer<K> keySerializer,
      RedisSerializer<V> valueSerializer) {
    this.keySerializer = keySerializer;
    this.valueSerializer = valueSerializer;
  }

  @Override
  public K decodeKey(ByteBuffer bytes) {
    return keySerializer.deserialize(decodeBuffer(bytes));
  }

  @Override
  public V decodeValue(ByteBuffer bytes) {
    return valueSerializer.deserialize(decodeBuffer(bytes));
  }

  @Override
  public ByteBuffer encodeKey(K key) {
    return encodeBuffer(keySerializer.serialize(key));
  }

  @Override
  public ByteBuffer encodeValue(V value) {
    return encodeBuffer(valueSerializer.serialize(value));
  }

  private byte[] decodeBuffer(ByteBuffer bytes) {
    ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
    byte[] array = new byte[buffer.readableBytes()];
    buffer.duplicate().readBytes(array);
    return array;
  }

  private ByteBuffer encodeBuffer(byte[] array) {
    if (array == null) {
      return ByteBuffer.wrap(new byte[0]);
    }
    return Unpooled.wrappedBuffer(array).nioBuffer();
  }

  public static <K, V> RedisCodec<K, V> create(RedisSerializer<K> keySerializer,
      RedisSerializer<V> valueSerializer) {
    return new DefaultRedisCodec<>(keySerializer, valueSerializer);
  }
}
