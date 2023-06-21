package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a bullet journal event.
 */
public class Event {
  private String name;
  private String description;
  private DayEnum dayEnum;
  private Time startTime;
  private int duration;

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

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public DayEnum getDay() {
    return this.dayEnum;
  }

  public Time getStartTime() {
    return this.startTime;
  }

  public int getDuration() {
    return this.duration;
  }
}
