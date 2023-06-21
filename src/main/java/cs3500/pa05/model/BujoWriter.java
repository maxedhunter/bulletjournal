package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Objects;

public class BujoWriter {
  private final Appendable appendable;

  /**
   * Initializes the BujoWriter
   *
   * @param appendable where the writer writes
   */
  public BujoWriter(Appendable appendable) {
    this.appendable = Objects.requireNonNull(appendable);
  }

  public Appendable write(JsonNode data) {
    ObjectMapper mapper = new ObjectMapper();
    String values;

    try {
      values = mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to write value JSON.");
    }

    try {
      appendable.append(values);
    } catch (IOException e) {
      throw new RuntimeException("Unable to append the provided week.");
    }

    return this.appendable;
  }
}
