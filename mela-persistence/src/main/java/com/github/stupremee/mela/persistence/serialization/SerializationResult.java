package com.github.stupremee.mela.persistence.serialization;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface SerializationResult {

  String getString(String identifier);

  Object getObject(String identifier);

  byte getByte(String identifier);

  short getShort(String identifier);

  int getInt(String identifier);

  long getLong(String identifier);

  float getFloat(String identifier);

  double getDouble(String identifier);

  BigInteger getBigInteger(String identifier);

  BigDecimal getBigDecimal(String identifier);

  void addString(String identifier, String value);

  void addObject(String identifier, Object value);

  void addByte(String identifier, byte value);

  void addShort(String identifier, short value);

  void addInt(String identifier, int value);

  void addLong(String identifier, long value);

  void addFloat(String identifier, float value);

  void addDouble(String identifier, double value);

  void addBigInteger(String identifier, BigInteger value);

  void addBigDecimal(String identifier, BigDecimal value);
}
