package cs3500.pa05.model;

import static cs3500.pa05.model.Time.stringToTime;
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

  @Test
  void testStringToTime() {
    Time expected = new Time(4, 3);
    assertEquals(expected.getMinute(), stringToTime("4:3").getMinute());
    assertEquals(expected.getMinute(), stringToTime("4:03").getMinute());

    assertEquals(expected.getHour(), stringToTime("4:3").getHour());
    assertThrows(RuntimeException.class,
        () -> stringToTime("alskdjfladjfalkdf"));
  }

  @Test
  void testToString() {
    Time exampleTime = new Time(15, 36);
    Time exampleTime2 = new Time(1, 3);

    assertEquals("15:36", exampleTime.toString());
    assertEquals("01:03", exampleTime2.toString());
  }
}