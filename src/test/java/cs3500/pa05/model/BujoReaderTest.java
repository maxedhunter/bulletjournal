package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a bujo reader.
 */
class BujoReaderTest {
  private static final String PATH = "src/test/resources/output.bujo";

  /**
   * Tests reading from a file.
   */
  @Test
  void testRead() {
    BujoReader reader = new BujoReader(PATH);
    Week week = reader.read();
    assertEquals("this week!", week.getName());

    List<Task> tasks = week.getTasks();
    Task task = tasks.get(0);
    assertEquals("todo", task.getName());
    assertEquals("do it!", task.getDescription());
    assertEquals(DayEnum.MONDAY, task.getDay());
    assertEquals(false, task.getCompletion());

    List<Event> events = week.getEvents();
    Event event = events.get(0);
    assertEquals("an event", event.getName());
    assertEquals("test event", event.getDescription());
    assertEquals(DayEnum.MONDAY, event.getDay());

    Time time = event.getStartTime();
    assertEquals(5, time.getHour());
    assertEquals(6, time.getMinute());

    assertEquals(7, event.getDuration());

    Day monday = week.getDays().get(DayEnum.MONDAY);
    assertEquals(1, monday.getTasks().size());
    assertEquals(1, monday.getEvents().size());
  }

  /**
   * Tests exception
   */
  @Test
  void testException() {
    BujoReader reader = new BujoReader("thisFilesDoesntExist");
    assertThrows(RuntimeException.class,
        () -> reader.read());
  }
}