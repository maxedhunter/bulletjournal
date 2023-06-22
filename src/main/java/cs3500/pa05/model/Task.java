package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a task.
 */
public class Task {
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
   * Returns the name of a task
   *
   * @return the name of a task
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the description of a task
   *
   * @return description
   */
  public String getDescription() {
    return this.description;
  }


  /**
   * Parses links from description (assuming there is a space following them or it reaches
   * the end of the string)
   *
   * @return valid urls
   */
  public List<String> parseLinks() {
    List<String> links = new ArrayList<>();
    String lowerCaseDesc = this.description.toLowerCase();
    String lowerCaseDescCopy = lowerCaseDesc;

    int start;
    int end;

    while (lowerCaseDesc.contains("http:")) {
      // if the string contains a link with http
      start = lowerCaseDesc.indexOf("http:");

      // if it doesn't exist
      if (lowerCaseDesc.indexOf(" ", start) == -1) {
        end = lowerCaseDesc.length();
      } else {
        end = lowerCaseDesc.indexOf(" ", start);
      }

      String temp = lowerCaseDesc.substring(start, end);
      links.add(temp);
      lowerCaseDesc = lowerCaseDesc.substring(end);
    }

    int startCopy;
    int endCopy;

    while (lowerCaseDescCopy.contains("https:")) {
      startCopy = lowerCaseDescCopy.indexOf("https:");

      // if it doesn't exist
      if (lowerCaseDescCopy.indexOf(" ", startCopy) == -1) {
        endCopy = lowerCaseDescCopy.length();
      } else {
        endCopy = lowerCaseDescCopy.indexOf(" ", startCopy);
      }

      String temp = lowerCaseDescCopy.substring(startCopy, endCopy);
      links.add(temp);
      lowerCaseDescCopy = lowerCaseDescCopy.substring(endCopy);
    }

    return links;
  }

  /**
   * Returns day of a task
   *
   * @return day enum
   */
  public DayEnum getDay() {
    return this.dayEnum;
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

  /**
   * Sets the name of a task to the new name
   *
   * @param newName for task to be set to
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Sets the description of a task to the new one
   *
   * @param newDescription for description to be set to
   */
  public void setDescription(String newDescription) {
    this.description = newDescription;
  }
}
