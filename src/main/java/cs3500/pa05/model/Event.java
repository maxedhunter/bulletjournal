package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a task.
 */
public class Event {
  private String name;
  private String description;
  private Day day;
  private String startTime;
  private String duration;

  /**
   * Initializes the values for a event.
   *
   * @param name - name of the event
   * @param description - description of the event
   * @param day - day of the event
   * @param time - when does the event start
   * @param duration - the duration of the event
   */
  @JsonCreator
  public Event(@JsonProperty("name")  String name,
               @JsonProperty("description") String description,
               @JsonProperty("day") Day day,
               @JsonProperty("startTime") String time,
               @JsonProperty("duration") String duration) {
    this.name = name;
    this.description = description;
    this.day = day;
    this.startTime = time;
    this.duration = duration;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public Day getDay() {
    return this.day;
  }


  public String getStartTime() {
    return this.startTime;
  }

  public String getDuration() {
    return this.duration;
  }
}
