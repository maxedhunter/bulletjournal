package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.json.JsonUtils;
import cs3500.pa05.json.WeekJson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            Days.MONDAY, false)));
    List<Event> events =
        new ArrayList<>(Arrays.asList(new Event("an event",
            "test event", Days.SUNDAY, new Time(5, 6), 7)));
    List<Day> day = new ArrayList<>(Arrays.asList(new Day(Days.MONDAY, tasks, null, events)));

    WeekJson weekJson = new WeekJson("This week", tasks, events)

    BujoWriter writer = new BujoWriter(new StringBuilder());
    assertEquals("", writer.write(week));
  }

  /**
   * //TODO
   */
  private JsonNode createWeek(String weekName, Record tasks, Record events, Record days) {
    WeekJson week =
        new WeekJson(weekName, JsonUtils.serializeRecord(tasks), JsonUtils.serializeRecord(events),
            JsonUtils.serializeRecord(days));
    return JsonUtils.serializeRecord(week);
  }
}