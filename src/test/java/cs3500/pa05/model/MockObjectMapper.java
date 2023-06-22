package cs3500.pa05.model;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a mock object mapper
 */
public class MockObjectMapper extends ObjectMapper {
  /**
   * Throws an error.
   *
   * @param fromValue starting object
   * @param toValueType name of class
   * @param <T> class type of final object
   * @return throws an error
   */
  @Override
  public <T> T convertValue(Object fromValue, Class<T> toValueType) {
    throw new IllegalArgumentException();
  }
}
