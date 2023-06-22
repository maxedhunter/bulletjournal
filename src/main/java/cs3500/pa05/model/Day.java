package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the bullets for a day.
 */
public class Day {
  private final List<Task> tasks;
  private final List<Task> completedTasks;
  private final List<Event> events;

  /**
   * Initializes the value for a day
   *
   * @param tasks list of all tasks
   * @param completedTasks list of completed tasks
   * @param events list of all events
   */
  @JsonCreator
  public Day(@JsonProperty("tasks") List<Task> tasks,
             @JsonProperty("completed-tasks") List<Task> completedTasks,
             @JsonProperty("events") List<Event> events) {
    this.tasks = tasks;
    this.completedTasks = completedTasks;
    this.events = events;
  }

  /**
   * Returns all the tasks for a day
   *
   * @return tasks for a day
   */
  public List<Task> getTasks() {
    return this.tasks;
  }

  /**
   * Returns all the completed tasks for a day
   *
   * @return completed tasks
   */
  public List<Task> getCompletedTasks() {
    return this.completedTasks;
  }

  /**
   * Returns the events of a day
   *
   * @return a day's events
   */
  public List<Event> getEvents() {
    return this.events;
  }
}
