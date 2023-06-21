package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a bujo reader.
 */
class BujoReaderTest {
  private static final String PATH = "src/test/resources/output.bujo";

  @Test
  void testRead() {
    BujoReader reader = new BujoReader(PATH);
    Week week = reader.read();
    assertEquals("this week!", week.getName());

    List<Task> tasks = week.getTasks();
    Task task = tasks.get(0);
    assertEquals("todo", task.getName());
  }

  @Test
  void testParseWeek() {
  }
}