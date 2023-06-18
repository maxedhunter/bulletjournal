package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Represents tests for time
 */
class TimeTest {
  /**
   * Tests constructing a valid time.
   */
  @Test
  void testConstructor() {
    Time time = new Time(5, 6);
    assertEquals(5, time.getHour());
    assertEquals(6, time.getMinute());
  }

  /**
   * Tests invalid hour inputs
   */
  @Test
  void testConstructorExceptions() {
    assertThrows(IllegalArgumentException.class,
        () -> new Time(-1, 6));

    assertThrows(IllegalArgumentException.class,
        () -> new Time(25, 6));
  }

  /**
   * Tests invalid minute inputs
   */
  @Test
  void testConstructorExceptions2() {
    assertThrows(IllegalArgumentException.class,
        () -> new Time(5, -1));
    assertThrows(IllegalArgumentException.class,
        () -> new Time(5, 60));
  }
}