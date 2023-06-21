package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.json.DaysJson;
import cs3500.pa05.json.EventsJson;
import cs3500.pa05.json.JsonUtils;
import cs3500.pa05.json.TasksJson;
import cs3500.pa05.json.WeekJson;
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
  private BujoWriter bujoWriter;

  @BeforeEach
  public void setUp() {
    this.appendable = new StringBuilder();
    this.bujoWriter = new BujoWriter(this.appendable);
  }

  /**
   * Tests the write function.
   */
  @Test
  void testWrite() {
    List<Task> tasks =
        new ArrayList<>(Arrays.asList(new Task("todo", "do it!",
            DayEnum.MONDAY, false)));
    TasksJson tasksJson = new TasksJson(tasks);

    List<Event> events =
        new ArrayList<>(Arrays.asList(new Event("an event",
            "test event", DayEnum.SUNDAY, new Time(5, 6), 7)));
    EventsJson eventsJson = new EventsJson(events);

    Map<DayEnum, Day> days = new HashMap<>();
    days.put(DayEnum.FRIDAY, new Day(tasks, null, events));
    DaysJson daysJson = new DaysJson(days);

    JsonNode weekJson = createWeek("this week!", tasksJson, eventsJson, daysJson);

    BujoWriter writer = new BujoWriter(new StringBuilder());
    assertEquals("", writer.write(weekJson));
  }

  /**
   * //TODO
   */
  private JsonNode createWeek(String weekName, Record tasks, Record events, Record days) {
    WeekJson week =
        new WeekJson(weekName,
            JsonUtils.serializeRecord(tasks),
            JsonUtils.serializeRecord(events),
            JsonUtils.serializeRecord(days));
    return JsonUtils.serializeRecord(week);
  }
}