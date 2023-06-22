package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents time (from 00:00 to 24:00).
 */
public class Time {
  private final int hour;
  private final int minute;

  /**
   * Initializes values for time
   *
   * @param hour   the hour (from 0 - 24)
   * @param minute minutes (from 0 to 60)
   */
  @JsonCreator
  public Time(@JsonProperty("hour") int hour,
              @JsonProperty("minute") int minute) {
    if ((hour >= 0) && (hour <= 24)) {
      this.hour = hour;
    } else {
      throw new IllegalArgumentException("Please provide a valid hour");
    }

    if ((minute >= 0) && (minute <= 59)) {
      this.minute = minute;
    } else {
      throw new IllegalArgumentException("Please provide a valid minute");
    }
  }

  /**
   * Returns the hour
   *
   * @return the hour
   */
  public int getHour() {
    return this.hour;
  }

  /**
   * Returns the minute
   *
   * @return the minute
   */
  public int getMinute() {
    return this.minute;
  }

  /**
   * Converts a string into a time.
   *
   * @param string to be converted into time
   * @return returns a time
   */
  public static Time stringToTime(String string) {
    Time time;
    try {
      int colon = string.indexOf(":");
      int hourString = Integer.parseInt(string.substring(0, colon));
      int minuteString = Integer.parseInt(string.substring(colon + 1));
      time = new Time(hourString, minuteString);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Unable to parse time");
    }
    return time;
  }

  /**
   * Converts a time into its string form
   *
   * @return the string version of a time
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (hour < 10) {
      builder.append("0").append(hour);
    } else {
      builder.append(hour);
    }

    builder.append(":");

    if (minute < 10) {
      builder.append("0").append(minute);
    } else {
      builder.append(minute);
    }

    return builder.toString();
  }
}
