package cs3500.pa05.model;

import java.util.List;

public interface IWeek {
  /**
   * retrieves tasks of a week
   *
   * @return - list of tasks
   */
  List<Task> getTasks();

  /**
   * retrieves events of a week
   *
   * @return - list of events taking place
   */
  List<Event> getEvents();

  /**
   * retrieves the weeks name
   *
   * @return - the name
   */
  String getName();
}
