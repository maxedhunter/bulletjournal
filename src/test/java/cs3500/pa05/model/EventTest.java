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
    assertEquals("an event", event.getName());
    event.setName("beep");
    assertEquals("beep", event.getName());
  }

  /**
   * Tests setting the description for an event
   */
  @Test
  void testSetDescription() {
    assertEquals("an event description", event.getDescription());
    event.setDescription("beep");
    assertEquals("beep", event.getDescription());
  }

  /**
   * Tests setting a start time for an event
   */
  @Test
  void testSetStartTime() {
    Time originalTime = event.getStartTime();
    assertEquals(5, originalTime.getHour());
    assertEquals(6, originalTime.getMinute());

    event.setStartTime(new Time(8, 9));
    Time newTime = event.getStartTime();
    assertEquals(8, newTime.getHour());
    assertEquals(9, newTime.getMinute());
  }

  /**
   * Tests setting a duration for an event
   */
  @Test
  void testSetDuration() {
    assertEquals(6, event.getDuration());
    event.setDuration(5);
    assertEquals(5, event.getDuration());
  }

  /**
   * Tests getting day
   */
  @Test
  void testGetDay() {
    assertEquals(DayEnum.TUESDAY, event.getDay());
  }
}