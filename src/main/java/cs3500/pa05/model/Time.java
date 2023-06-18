package cs3500.pa05.model;

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
  public Time(int hour, int minute) {
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
  public  int getMinute() {
    return this.minute;
  }
}
