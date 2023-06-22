package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a bullet journal event.
 */
public class Event {
  private String name;
  private String description;
  private final DayEnum dayEnum;
  private Time startTime;
  private int duration;

  /**
   * Initializes the values of an event
   *
   * @param name name
   * @param description description
   * @param dayEnum which day of the week
   * @param startTime a time
   * @param duration how long the even lasts for
   */
  @JsonCreator
  public Event(@JsonProperty("name") String name,
               @JsonProperty("description") String description,
               @JsonProperty("day") DayEnum dayEnum,
               @JsonProperty("time") Time startTime,
               @JsonProperty("duration") int duration) {
    this.name = name;
    this.description = description;
    this.dayEnum = dayEnum;
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * Returns an event's name
   *
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns an event's description
   *
   * @return description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns which day the event falls on
   *
   * @return day enum
   */
  public DayEnum getDay() {
    return this.dayEnum;
  }

  /**
   * Returns the start time of an event
   *
   * @return the start time
   */
  public Time getStartTime() {
    return this.startTime;
  }

  /**
   * Return's an event duration
   *
   * @return duration
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Sets the name of an event
   *
   * @param newName to be set
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Sets the description of an event
   *
   * @param newDescription to be set
   */
  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  /**
   * Sets the time of an event
   *
   * @param newTime to be set
   */
  public void setStartTime(Time newTime) {
    this.startTime = newTime;
  }

  /**
   * Sets the duration of an event
   *
   * @param newDuration to be set
   */
  public void setDuration(int newDuration) {
    this.duration = newDuration;
  }
}
