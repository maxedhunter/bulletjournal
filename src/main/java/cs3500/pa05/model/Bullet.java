package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bullet
 */
abstract class Bullet {
  private String name;
  private String description;
  private final DayEnum dayEnum;

  /**
   * Initializes the values of a bullet
   *
   * @param name name of a bullet
   * @param description description of a bullet
   * @param dayEnum day of a bullet
   */
   Bullet(String name, String description, DayEnum dayEnum) {
    this.name = name;
    this.description = description;
    this.dayEnum = dayEnum;
  }

  /**
   * Returns the name of a bullet
   *
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the description of a bullet
   *
   * @return description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns the day for a bullet
   *
   * @return day
   */
  public DayEnum getDay() {
    return this.dayEnum;
  }

  /**
   * Sets the name of a bullet to the new name
   *
   * @param newName for bullet to be set to
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Sets the description of a bullet to the new one
   *
   * @param newDescription for bullet to be set to
   */
  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  /**
   * Parses links from description (assuming there is a space following them or it reaches
   * the end of the string)
   *
   * @return valid urls
   */
  public List<String> parseLinks(String string) {
    List<String> links = new ArrayList<>();
    String lowerCaseDesc = string.toLowerCase();
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

}
