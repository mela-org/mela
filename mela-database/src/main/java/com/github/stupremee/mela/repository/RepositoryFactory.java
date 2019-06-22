package com.github.stupremee.mela.repository;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 11.06.19
 */
public interface RepositoryFactory {

  /**
   * Creates a new {@link ReactiveRepository}.
   *
   * @param entityClass The class of the entity that should be stored in the repository
   * @param identifierClass The unique identifier that should be used
   * @return The created {@link ReactiveRepository}
   */
  <IdentifierT, EntityT> ReactiveRepository<IdentifierT, EntityT> createReactiveRepository(
      Class<IdentifierT> identifierClass,
      Class<EntityT> entityClass);

}