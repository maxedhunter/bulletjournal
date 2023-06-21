package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for task
 */
class TaskTest {
  Task task;
  Task task2;

  /**
   * Sets up default test tasks
   */
  @BeforeEach
  void setUp() {
    task = new Task("name", "description", DayEnum.MONDAY, false);
    task2 = new Task("name2", "description2", DayEnum.FRIDAY, true);
  }

  /**
   * Tests getting the name
   */
  @Test
  void testGetName() {
    assertEquals("name", task.getName());
    assertEquals("description", task.getDescription());
    assertEquals(DayEnum.MONDAY, task.getDay());
    assertEquals(false, task.getCompletion());
  }

  /**
   * Tests getting the completion string for button
   */
  @Test
  void testGetCompletionString() {
    assertEquals("(Incomplete)", task.getCompletionString());
    assertEquals("(Complete)", task2.getCompletionString());
  }

  /**
   * Tests setting completion
   */
  @Test
  void testSetCompletion() {
    assertEquals(false, task.getCompletion());
    task.setCompletion();
    assertEquals(true, task.getCompletion());
  }

  /**
   * Tests setting names
   */
  @Test
  void testSetName() {
    assertEquals("name", task.getName());
    task.setName("beep");
    assertEquals("beep", task.getName());
  }

  /**
   * Tests setting description
   */
  @Test
  void testSetDescription() {
    assertEquals("description", task.getDescription());
    task.setDescription("hi");
    assertEquals("hi", task.getDescription());
  }
}