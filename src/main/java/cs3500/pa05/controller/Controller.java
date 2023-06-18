package cs3500.pa05.controller;

/**
 * Represents a controller for a Journal.
 */
public interface Controller {
  /**
   * Initializes a Journal
   *
   * @throws IllegalStateException if the journal is not defined
   */
  void run() throws IllegalStateException;
}
