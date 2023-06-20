package cs3500.pa05.model;

/**
 * Represents a bullet journal event.
 */
public class Event {
  private String name;
  private String description;
  private Days days;
  private Time startTime;
  private int duration;


  public Event(String name, String description, Days days, Time startTime, int duration) {
    this.name = name;
    this.description = description;
    this.days = days;
    this.startTime = startTime;
    this.duration = duration;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public Days getDay() {
    return this.days;
  }

  public Time getStartTime() {
    return this.startTime;
  }

  public int getDuration() {
    return this.duration;
  }
}
