package com.github.stupremee.mela.persistence;

public interface Mergeable<EntityT extends DataTransferObject> {

  EntityT merge(EntityT entity);
}
