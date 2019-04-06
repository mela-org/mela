package com.github.stupremee.mela.repository;

import com.github.stupremee.mela.beans.Bean;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 06.04.2019
 */
@SuppressWarnings("unused")
public interface Repository<T extends Bean> {

  /**
   * Searches all beans in this repository that matches the {@link Specification}.
   *
   * @param query The {@link Specification} the beans should match
   * @return A {@link Flux} containing all found beans
   */
  Flux<T> query(Specification<T> query);

  /**
   * Returns the first entity that matches {@link Specification the query} by calling {@link
   * #query(Specification)} and get the first.
   *
   * @param query The {@link Specification} the beans should match
   * @return A {@link Flux} containing all found beans
   */
  Mono<T> queryFirst(Specification<T> query);

  /**
   * Tries to find a bean with this id.
   *
   * @param id The id of the bean
   * @return A {@link Mono} containing the bean that was found or a empty mono if no bean was found
   */
  Mono<T> findById(Snowflake id);

  /**
   * Tries to find a bean for every id in the given {@link Iterable}.
   *
   * @param ids The ids of the beans
   * @return A {@link Flux} containing all the beans that was found or a empty flux if no bean was
   *     found
   */
  Flux<T> findAllById(Iterable<Snowflake> ids);

  /**
   * Returns all beans that are stored in this repository.
   *
   * @return A {@link Flux} containing all beans
   */
  Flux<T> getAll();

  /**
   * Deletes a bean by his id.
   *
   * @param bean The bean to remove
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  default Mono<Void> remove(T bean) {
    return deleteById(Snowflake.of(bean.getId()));
  }

  /**
   * Deletes a bean by his id.
   *
   * @param id The id of the bean
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  Mono<Void> deleteById(Snowflake id);

  /**
   * Deletes all beans that are given in the {@link Iterable}.
   *
   * @param beans The beans to delete
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  Mono<Void> deleteAll(Iterable<T> beans);

  /**
   * Deletes all beans from this repository.
   */
  Mono<Void> deleteAll();

  /**
   * Saves a bean to this repository.
   *
   * @param bean The bean
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  Mono<Void> save(T bean);

  /**
   * Saves all beans that are given to the database.
   *
   * @param beans The beans to save
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  Mono<Void> saveMany(Iterable<T> beans);

  /**
   * Finds a bean by his id and then replaces the old bean with the new given bean.
   *
   * @param bean The new bean
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  Mono<Void> update(T bean);

  /**
   * Saves all beans in the {@link Iterable} to this repository.
   *
   * @param beans The beans to save
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  Mono<Void> updateMany(Iterable<T> beans);

  /**
   * Checks if an bean exists in this repository.
   *
   * @param bean The bean to check
   * @return A {@link Mono} that contains the boolean which indicate if the bean exist or not
   */
  default Mono<Boolean> exists(T bean) {
    return exists(Snowflake.of(bean.getId()));
  }

  /**
   * Checks if an bean exists in this repository.
   *
   * @param id The id of the bean
   * @return A {@link Mono} that contains the boolean which indicate if the bean exist or not
   */
  Mono<Boolean> exists(Snowflake id);

  /**
   * Counts all beans in this repository.
   *
   * @return A {@link Mono} that contains the number of beans in this repository
   */
  Mono<Long> count();
}
