package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the bullets for a day.
 */
public class Day {
  private List<Task> tasks;
  private List<Task> completedTasks;
  private List<Event> events;

  @JsonCreator
  public Day(@JsonProperty("tasks") List<Task> tasks,
             @JsonProperty("completed-tasks") List<Task> completedTasks,
             @JsonProperty("events") List<Event> events) {
    this.tasks = tasks;
    this.completedTasks = completedTasks;
    this.events = events;
  }

  public List<Task> getTasks() {
    return this.tasks;
  }

  public List<Task> getCompletedTasks() {
    return this.completedTasks;
  }

  public List<Event> getEvents() {
    return this.events;
  }
}
