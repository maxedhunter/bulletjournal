package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a task.
 */
public class Task {
  private String name;
  private String description;
  private Days days;
  private boolean isDone;

  /**
   * Initializes the values for a task.
   *
   * @param name - name of the task
   * @param description - description of the task
   * @param days - day of the task
   * @param isDone - is the task finished
   */
  @JsonCreator
  public Task(@JsonProperty("name") String name,
              @JsonProperty("description") String description,
              @JsonProperty("day") Days days,
              @JsonProperty("completion") boolean isDone) {
    this.name = name;
    this.description = description;
    this.days = days;
    this.isDone = isDone;
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

  public boolean getIsDone(){
    return this.isDone;
  }

  public String getCompletion() {
    if (this.isDone) {
      return "Complete";
    } else {
      return "Undone";
    }
  }

  public void setCompletion() {
    this.isDone = true;
  }
}
