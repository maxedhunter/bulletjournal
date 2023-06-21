package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
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
        DayEnum.MONDAY, false)));
    tasksJson = new TasksJson(tasks);
    events = new ArrayList<>(Arrays.asList(new Event("an event",
        "test event", DayEnum.MONDAY, new Time(5, 6), 7)));
    eventsJson = new EventsJson(events);
    days = new HashMap<>();
    days.put(DayEnum.MONDAY, new Day(tasks, null, events));
    daysJson = new DaysJson(days);
    weekJson = createWeek("this week!", tasksJson, eventsJson, daysJson);
  }

  /**
   * Tests the write function.
   */
  @Test
  void testWrite() {
    BujoWriter writer = new BujoWriter(PATH);
    writer.writeToFile(weekJson, new StringBuilder());

    String expectedString = "{\"name\":\"this week!\",\"tasks\":{\"tasks\":[{\"name\":\""
            + "todo\",\"description\":\"do it!\",\"day\":\"MONDAY\",\"completion\":false}]},\""
            + "events\":{\"events\":[{\"name\":\"an event\",\"description\":\"test event\",\""
            + "day\":\"MONDAY\",\"duration\":7,\"startTime\":{\"hour\":5,\"minute\":6}}]},\""
            + "days\":{\"days\":{\"MONDAY\":{\"tasks\":[{\"name\":\"todo\",\"description\":\""
            + "do it!\",\"day\":\"MONDAY\",\"completion\":false}],\"events\":[{\"name\":\"an "
            + "event\",\"description\":\"test event\",\"day\":\"MONDAY\",\"duration\":7,\""
            + "startTime\":{\"hour\":5,\"minute\":6}}],\"completedTasks\":null}}}}";

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
   * Creates the week (adapted from createMessage)
   */
  private JsonNode createWeek(String weekName, Record tasks, Record events, Record days) {
    WeekJson week =
        new WeekJson(weekName,
            JsonUtils.serializeRecord(tasks, new ObjectMapper()),
            JsonUtils.serializeRecord(events, new ObjectMapper()),
            JsonUtils.serializeRecord(days, new ObjectMapper()));
    return JsonUtils.serializeRecord(week, new ObjectMapper());
  }


  /**
   * Try converting the current test log to a string of a certain class. Modified from
   * mock example.
   *
   * @param <T> Type to try converting the current test stream to.
   */
  private <T> void responseToClass() {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(this.appendable.toString());

      // first convert to weekjson
      WeekJson weekJson = jsonParser.readValueAs(WeekJson.class);

      // now parse tasks of weekjson
      String tasks = weekJson.tasks().toString();
      JsonParser jsonParser1 = new ObjectMapper().createParser(tasks);
      jsonParser1.readValueAs(TasksJson.class);

      // now parse events
      String events = weekJson.events().toString();
      JsonParser jsonParser2 = new ObjectMapper().createParser(events);
      jsonParser2.readValueAs(EventsJson.class);

      // finally parse days
      String days = weekJson.days().toString();
      JsonParser jsonParser3 = new ObjectMapper().createParser(days);
      jsonParser3.readValueAs(DaysJson.class);

    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      e.printStackTrace();
      fail();
    }
  }
}