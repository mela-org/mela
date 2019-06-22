package com.github.stupremee.mela.repository;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 11.06.19
 */
public interface RepositoryFactory {

  /**
   * Creates a new {@link ReactiveRepository}.
   *
   * @return The created {@link ReactiveRepository}
   */
  <IdentifierT, EntityT> ReactiveRepository<IdentifierT, EntityT> createReactiveRepository(
      Class<IdentifierT> identifierClass,
      Class<EntityT> entityClass);

}