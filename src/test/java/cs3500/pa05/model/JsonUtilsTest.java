package cs3500.pa05.model;

import static cs3500.pa05.model.JsonUtils.serializeRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for JSON util.
 */
class JsonUtilsTest {
  JsonUtils jsonUtils = new JsonUtils();

  /**
   * Tests exception
   */
  @Test
  void testSerializeRecordException() {
    assertThrows(IllegalArgumentException.class,
        () -> jsonUtils.serializeRecord(new TasksJson(
                new ArrayList<>(Arrays.asList(new Task("hi", "hello",
                    DayEnum.TUESDAY, false)))),
            new MockObjectMapper()));
  }

  @Test
  void testSerializeRecord() {
    JsonNode task = serializeRecord(new TasksJson(
        new ArrayList<>(Arrays.asList(new Task("hi", "hello",
            DayEnum.TUESDAY, false)))), new ObjectMapper());

    assertEquals("{\"tasks\":[{\"name\":\"hi\",\"description\":\"hello\",\"day\":"
        + "\"TUESDAY\",\"completion\":false}]}", task.toString());
  }
}