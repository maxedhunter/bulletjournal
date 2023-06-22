package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
    assertFalse(task.getCompletion());
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
    assertFalse(task.getCompletion());
    task.setCompletion();
    assertTrue(task.getCompletion());
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

  /**
   * Tests getting links
   */
  @Test
  void testParseLink() {
    Task taskWithUrl = new Task("", "This is a sample text containing a URL: "
        + "https://www.example.com and another URL: http://www.google.com",
        DayEnum.TUESDAY, true);
    List<String> links = taskWithUrl.parseLinks(taskWithUrl.getDescription());
    assertEquals("https://www.example.com", links.get(1));
    assertEquals("http://www.google.com", links.get(0));

    // tests the opposite edge case
    Task taskWithUrl2 = new Task("", "This is a sample text containing a URL: "
        + "http://www.google.com and another URL: https://www.example.com", DayEnum.MONDAY, true);
    List<String> links2 = taskWithUrl2.parseLinks(taskWithUrl2.getDescription());
    assertEquals("https://www.example.com", links2.get(1));
    assertEquals("http://www.google.com", links2.get(0));
  }
}