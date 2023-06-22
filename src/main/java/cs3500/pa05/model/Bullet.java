package cs3500.pa05.model;

/**
 * Represents a bullet
 */
public interface Bullet {
  /**
   * Returns the name of a bullet
   *
   * @return name
   */
  String getName();

  /**
   * Returns the description of a bullet
   *
   * @return description
   */
  String getDescription();

  /**
   * Returns the day for a bullet
   *
   * @return day
   */
  DayEnum getDay();

  /**
   * Sets the name of a bullet to the new name
   *
   * @param newName for bullet to be set to
   */
  void setName(String newName);

  /**
   * Sets the description of a bullet to the new one
   *
   * @param newDescription for bullet to be set to
   */
  public void setDescription(String newDescription);
}
