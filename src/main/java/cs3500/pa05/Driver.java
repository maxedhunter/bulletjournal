package cs3500.pa05;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Represents a Journal application.
 */
public class Driver extends Application {
  /**
   * Starts the GUI for a Journal
   *
   * @param stage the JavaFX stage to add elements to
   */
  @Override
  public void start(Stage stage) {
    stage.setTitle("Journal");
  }

  /**
   * Entry point for a Journal
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch();
  }
}
