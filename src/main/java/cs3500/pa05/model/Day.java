package cs3500.pa05.model;

import java.util.List;

/**
 * Represents the bullets for a day.
 */
public class Day {
  Days day;
  private List<Task> tasks;
  private List<Task> completedTasks;
  private List<Event> events;

  Day(Days day, List<Task> tasks, List<Task> completedTasks, List<Event> events) {
    this.day = day;
    this.tasks = tasks;
    this.completedTasks = completedTasks;
    this.events = events;
  }
}
