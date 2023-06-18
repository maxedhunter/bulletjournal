package cs3500.pa05.model;

import java.util.List;
import javafx.event.Event;

/**
 * represents a week for a bullet journal
 */
public class Week implements IWeek {
  private String name;
  private final List<Task> tasks;
  private final List<Event> events;

  /**
   * constructs a week
   *
   * @param name - the name of the week
   * @param tasks - tasks for the week
   * @param events - events during the week
   */
  public Week(String name, List<Task> tasks, List<Event> events) {
    this.name = name;
    this.tasks = tasks;
    this.events = events;
  }


  @Override
  public List<Task> getTasks() {
    return this.tasks;
  }

  @Override
  public List<Event> getEvents() {
    return this.events;
  }

  @Override
  public String getName() {
    return this.name;
  }
}
