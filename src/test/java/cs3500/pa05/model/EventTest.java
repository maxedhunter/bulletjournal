package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {
  Event event;

  @BeforeEach
  void setUp() {
    event = new Event("an event", "an event description",
        DayEnum.TUESDAY, new Time(5, 6), 6);
  }

  /**
   * Tests setting the name for an event.
   */
  @Test
  void testSetName() {
  }

  /**
   * Tests setting the description for an event
   */
  @Test
  void testSetDescription() {
  }

  /**
   * Tests setting a start time for an event
   */
  @Test
  void testSetStartTime() {
  }

  /**
   * Tests setting a duration for an event
   */
  @Test
  void testSetDuration() {
    assertEquals()
  }
}