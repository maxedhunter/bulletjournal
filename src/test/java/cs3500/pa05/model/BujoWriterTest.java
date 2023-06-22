package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for BujoWriter
 */
class BujoWriterTest {
  private Appendable appendable;

  private List<Task> tasks;
  private TasksJson tasksJson;
  private List<Event> events;
  private EventsJson eventsJson;
  Map<DayEnum, Day> days;
  DaysJson daysJson;
  JsonNode weekJson;

  private static final String PATH = "src/test/resources/output.bujo";

  @BeforeEach
  public void setUp() {
    this.appendable = new StringBuilder();
    tasks = new ArrayList<>(Arrays.asList(new Task("todo", "do it!",
        DayEnum.MONDAY, true)));
    tasksJson = new TasksJson(tasks);
    events = new ArrayList<>(Arrays.asList(new Event("an event",
        "test event", DayEnum.MONDAY, new Time(5, 6), 7)));
    eventsJson = new EventsJson(events);
    days = new HashMap<>();
    days.put(DayEnum.MONDAY, new Day(tasks, tasks, events));
    daysJson = new DaysJson(days);
    weekJson = createWeek("this week!", tasksJson, eventsJson,
        daysJson, 5, 5);
  }

  /**
   * Tests the write function.
   */
  @Test
  void testWrite() {
    BujoWriter writer = new BujoWriter(PATH);
    writer.writeToFile(weekJson, new StringBuilder());

    String expectedString = "{\"name\":\"this week!\",\"tasks\":{\"tasks\":[{\"name\":"
        + "\"todo\",\"description\":\"do it!\",\"day\":\"MONDAY\",\"completion\":true}]},"
        + "\"events\":{\"events\":[{\"name\":\"an event\",\"description\":\"test event\","
        + "\"day\":\"MONDAY\",\"duration\":7,\"startTime\":{\"hour\":5,\"minute\":6}}]},"
        + "\"days\":{\"days\":{\"MONDAY\":{\"tasks\":[{\"name\":\"todo\",\"description\":"
        + "\"do it!\",\"day\":\"MONDAY\",\"completion\":true}],\"events\":[{\"name\":\"an "
        + "event\",\"description\":\"test event\",\"day\":\"MONDAY\",\"duration\":7,\""
        + "startTime\":{\"hour\":5,\"minute\":6}}],\"completedTasks\":[{\"name\":\"todo\","
        + "\"description\":\"do it!\",\"day\":\"MONDAY\",\"completion\":true}]}}},\""
        + "max-tasks\":5,\"max-events\":5}";

    String actualContent;

    try {
      actualContent = Files.readString(Path.of(PATH));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(expectedString, actualContent);
  }

  /**
   * Test mock exceptions.
   */
  @Test
  void testException() {
    BujoWriter writerException = new BujoWriter(PATH);
    assertThrows(RuntimeException.class,
        () -> writerException.writeToFile(weekJson, new MockAppendable()));
  }

  /**
   * Test mock exceptions.
   */
  @Test
  void testException2() {
    BujoWriter writerException = new BujoWriter("asldkfjalskdjf");
    assertThrows(IllegalArgumentException.class,
        () -> writerException.writeToFile(weekJson, new StringBuilder()));
  }

  /**
   * Creates the week (adapted from createMessage)
   */
  private JsonNode createWeek(String weekName, Record tasks,
                              Record events, Record days, int maxTasks, int maxEvents) {
    WeekJson week =
        new WeekJson(weekName,
            JsonUtils.serializeRecord(tasks, new ObjectMapper()),
            JsonUtils.serializeRecord(events, new ObjectMapper()),
            JsonUtils.serializeRecord(days, new ObjectMapper()),
            maxTasks, maxEvents);
    return JsonUtils.serializeRecord(week, new ObjectMapper());
  }
}