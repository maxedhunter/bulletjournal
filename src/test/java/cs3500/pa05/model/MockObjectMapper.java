package cs3500.pa05.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MockObjectMapper extends ObjectMapper {
  @Override
  public <T> T convertValue(Object fromValue, Class<T> toValueType) {
    throw new IllegalArgumentException();
  }
}
