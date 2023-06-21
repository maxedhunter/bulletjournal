package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class BujoWriter {
  private final Path writeTo;

  /**
   * Initializes writer.
   *
   * @param path
   */
  public BujoWriter(String path) {
    writeTo = Path.of(path);
  }

  /**
   * Writes the data to the file path.
   *
   * @param data data to write
   * @param appendable way to append
   */
  public void writeToFile(JsonNode data, Appendable appendable) {
    ObjectMapper mapper = new ObjectMapper();
    String values;

    Appendable appendToThis = appendable;

    try {
      values = mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to write value JSON.");
    }

    try {
      appendToThis.append(values);
    } catch (IOException e) {
      throw new RuntimeException("Unable to append the provided week.");
    }

    if (writeTo.toString().endsWith(".bujo")) {
      try {
        Files.write(writeTo, appendable.toString().getBytes());
      } catch (IOException e) {
        throw new RuntimeException("Unable to write to file");
      }

    } else {
      throw new IllegalArgumentException("Please provide a .bujo file path.");
    }
  }
}
