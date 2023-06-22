package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Represents a bullet journal file reader.
 */
public class BujoReader {

  private final Path path;

  /**
   * Initializes a Bujo reader.
   *
   * @param path to read from
   */
  public BujoReader(String path) {
    this.path = Path.of(path);
  }

  /**
   * Returns the week.
   *
   * @return the week
   */
  public Week read() {
    String content;

    try {
      content = Files.readString(this.path);
    } catch (IOException e) {
      throw new RuntimeException("Unable to read from this file");
    }

    Week week = parseWeek(content);
    return week;
  }


  /**
   * Parses a week
   *
   * @param content to be parsed
   * @return week object
   */
  public Week parseWeek(String content) {
    String name;
    List<Task> tasks;
    List<Event> events;
    Map<DayEnum, Day> days;

    try {
      JsonParser jsonParser = new ObjectMapper().createParser(content);
      WeekJson weekJson = jsonParser.readValueAs(WeekJson.class);
      name = weekJson.name();

      JsonParser jsonParser1 = new ObjectMapper().createParser(weekJson.tasks().toString());
      TasksJson tasksJson = jsonParser1.readValueAs(TasksJson.class);
      tasks = tasksJson.tasks();

      JsonParser jsonParser2 = new ObjectMapper().createParser(weekJson.events().toString());
      EventsJson eventsJson = jsonParser2.readValueAs(EventsJson.class);
      events = eventsJson.events();

      JsonParser jsonParser3 = new ObjectMapper().createParser(weekJson.days().toString());
      DaysJson daysJson = jsonParser3.readValueAs(DaysJson.class);
      days = daysJson.days();

    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      System.out.print(e);
      throw new IllegalArgumentException("Unable to parse content");
    }

    return new Week(name, tasks, events, days);
  }
}
