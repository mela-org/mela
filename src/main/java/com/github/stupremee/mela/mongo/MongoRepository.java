package com.github.stupremee.mela.mongo;

import com.github.stupremee.mela.beans.SnowflakeBean;
import com.github.stupremee.mela.repository.Repository;
import javax.annotation.Nonnull;
import org.bson.conversions.Bson;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 09.04.2019
 */
public interface MongoRepository<T extends SnowflakeBean> extends Repository<T> {

  /**
   * Searches all beans in this repository that matches the {@link Bson}.
   *
   * @param query The {@link Bson} the beans should match
   * @return A {@link Flux} containing all found beans
   */
  @Nonnull
  Flux<T> query(@Nonnull Bson query);

  /**
   * Returns the first entity that matches {@link Bson the query} by calling {@link
   * #query(Bson)} and get the first.
   *
   * @param query The {@link Bson} the beans should match
   * @return A {@link Flux} containing all found beans
   */
  @Nonnull
  Mono<T> queryFirst(@Nonnull Bson query);
}
