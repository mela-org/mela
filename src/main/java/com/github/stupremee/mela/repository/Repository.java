package com.github.stupremee.mela.repository;

import com.github.stupremee.mela.beans.Bean;
import discord4j.core.object.util.Snowflake;
import org.jetbrains.annotations.NotNull;
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
  @NotNull
  Flux<T> query(@NotNull Specification<T> query);

  /**
   * Returns the first entity that matches {@link Specification the query} by calling {@link
   * #query(Specification)} and get the first.
   *
   * @param query The {@link Specification} the beans should match
   * @return A {@link Flux} containing all found beans
   */
  @NotNull
  Mono<T> queryFirst(@NotNull Specification<T> query);

  /**
   * Tries to find a bean with this id.
   *
   * @param id The id of the bean
   * @return A {@link Mono} containing the bean that was found or a empty mono if no bean was found
   */
  @NotNull
  Mono<T> findById(@NotNull Snowflake id);

  /**
   * Tries to find a bean for every id in the given {@link Iterable}.
   *
   * @param ids The ids of the beans
   * @return A {@link Flux} containing all the beans that was found or a empty flux if no bean was
   *     found
   */
  @NotNull
  Flux<T> findAllById(@NotNull Iterable<Snowflake> ids);

  /**
   * Returns all beans that are stored in this repository.
   *
   * @return A {@link Flux} containing all beans
   */
  @NotNull
  Flux<T> getAll();

  /**
   * Deletes a bean by his id.
   *
   * @param bean The bean to delete
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  @NotNull
  default Mono<Void> delete(@NotNull T bean) {
    return deleteById(Snowflake.of(bean.getId()));
  }

  /**
   * Deletes a bean by his id.
   *
   * @param id The id of the bean
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  @NotNull
  Mono<Void> deleteById(@NotNull Snowflake id);

  /**
   * Deletes all beans that are given in the {@link Iterable}.
   *
   * @param beans The beans to delete
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  @NotNull
  Mono<Void> deleteAll(@NotNull Iterable<T> beans);

  /**
   * Deletes all beans from this repository.
   */
  @NotNull
  Mono<Void> deleteAll();

  /**
   * Saves a bean to this repository.
   *
   * @param bean The bean
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  @NotNull
  Mono<Void> save(@NotNull T bean);

  /**
   * Saves all beans that are given to the database.
   *
   * @param beans The beans to save
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  @NotNull
  Mono<Void> saveMany(@NotNull Iterable<T> beans);

  /**
   * Finds a bean by his id and then replaces the old bean with the new given bean.
   *
   * @param bean The new bean
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  @NotNull
  Mono<Void> update(@NotNull T bean);

  /**
   * Saves all beans in the {@link Iterable} to this repository.
   *
   * @param beans The beans to save
   * @return A {@link Mono} that completes when the bean was successfully deleted
   */
  @NotNull
  Mono<Void> updateMany(@NotNull Iterable<T> beans);

  /**
   * Checks if an bean exists in this repository.
   *
   * @param bean The bean to check
   * @return A {@link Mono} that contains the boolean which indicate if the bean exist or not
   */
  @NotNull
  default Mono<Boolean> exists(@NotNull T bean) {
    return exists(Snowflake.of(bean.getId()));
  }

  /**
   * Checks if an bean exists in this repository.
   *
   * @param id The id of the bean
   * @return A {@link Mono} that contains the boolean which indicate if the bean exist or not
   */
  @NotNull
  Mono<Boolean> exists(@NotNull Snowflake id);

  /**
   * Counts all beans in this repository.
   *
   * @return A {@link Mono} that contains the number of beans in this repository
   */
  @NotNull
  Mono<Long> count();
}
