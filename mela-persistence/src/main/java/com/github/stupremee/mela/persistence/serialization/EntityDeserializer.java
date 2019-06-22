package com.github.stupremee.mela.persistence.serialization;

import com.github.stupremee.mela.persistence.DataTransferObject;

public interface EntityDeserializer<EntityT extends DataTransferObject> {

  EntityT deserialize(SerializationResult result);
}
