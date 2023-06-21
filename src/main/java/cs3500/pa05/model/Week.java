package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * represents a week for a bullet journal
 */
public class Week  {
  private String name;
  private final List<Task> tasks;
  private final List<Event> events;
  private final Map<DayEnum, Day> days;

  /**
   * constructs a week
   *
   * @param name   - the name of the week
   * @param tasks  - tasks for the week
   * @param events - events during the week
   */
  @JsonCreator
  public Week(@JsonProperty("name") String name,
              @JsonProperty("tasks") List<Task> tasks,
              @JsonProperty ("events") List<Event> events,
              @JsonProperty ("days") Map<DayEnum, Day> days) {
    this.name = name;
    this.tasks = tasks;
    this.events = events;
    this.days = days;
  }

  public List<Task> getTasks() {
    return this.tasks;
  }

  public List<Event> getEvents() {
    return this.events;
  }

  public String getName() {
    return this.name;
  }

  public Map<DayEnum, Day> getDays() {
    return this.days;
  }
}
