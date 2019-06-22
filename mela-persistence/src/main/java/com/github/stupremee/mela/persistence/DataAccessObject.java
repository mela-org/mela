package com.github.stupremee.mela.persistence;

public interface DataAccessObject<IdentifierT, EntityT extends DataTransferObject> {

  void create(EntityT entity);

  EntityT read(IdentifierT identifier);

  void update(EntityT entity);

  void delete(IdentifierT identifier);
}
