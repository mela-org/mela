package com.github.stupremee.mela.persistence.serialization;

import com.github.stupremee.mela.persistence.DataTransferObject;

public interface EntitySerializer<EntityT extends DataTransferObject> {

  SerializationResult serialize(EntityT entity);
}
