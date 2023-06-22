package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents a task.
 */
public class Task implements Bullet {
  private String name;
  private String description;
  private final DayEnum dayEnum;
  private boolean completion;

  /**
   * Initializes the values for a task.
   *
   * @param name        - name of the task
   * @param description - description of the task
   * @param dayEnum     - day of the task
   * @param completion  - is the task finished
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

  /**
   * Returns task completion.
   *
   * @return completion
   */
  public boolean getCompletion() {
    return this.completion;
  }

  /**
   * Provides a string representation of completion.
   *
   * @return the completion string
   */
  @JsonIgnore
  public String getCompletionString() {
    if (this.completion) {
      return "(Complete)";
    } else {
      return "(Incomplete)";
    }
  }

  /**
   * Updates completion to true.
   */
  public void setCompletion() {
    this.completion = true;
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
