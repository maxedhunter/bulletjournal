package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a task.
 */
public class Task {
  private String name;
  private String description;
  private DayEnum dayEnum;
  private boolean completion;

  /**
   * Initializes the values for a task.
   *
   * @param name - name of the task
   * @param description - description of the task
   * @param dayEnum - day of the task
   * @param completion - is the task finished
   */
  @JsonCreator
  public Task(@JsonProperty("name") String name,
              @JsonProperty("description") String description,
              @JsonProperty("day") DayEnum dayEnum,
              @JsonProperty("completion") boolean completion) {
    this.name = name;
    this.description = description;
    this.dayEnum = dayEnum;
    this.completion = completion;
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

  public boolean getCompletion(){
    return this.completion;
  }

  public String getCompletionString() {
    if (this.completion) {
      return "(Complete)";
    } else {
      return "(Incomplete)";
    }
  }

  public void setCompletion() {
    this.completion = true;
  }
}
