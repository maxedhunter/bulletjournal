package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a bullet journal event.
 */
public class Event implements Bullet {
  private String name;
  private String description;
  private final DayEnum dayEnum;

  private Time startTime;
  private int duration;

  /**
   * Initializes the values of an event
   *
   * @param name        name
   * @param description description
   * @param dayEnum     which day of the week
   * @param startTime   a time
   * @param duration    how long the even lasts for
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

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public DayEnum getDay() {
    return this.dayEnum;
  }

  @Override
  public void setName(String newName) {
    this.name = newName;
  }

  @Override
  public void setDescription(String newDescription) {
    this.description = newDescription;
  }
}
