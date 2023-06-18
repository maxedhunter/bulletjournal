package cs3500.pa05;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.controller.ControllerImpl;
import cs3500.pa05.controller.TaskControllerImpl;
import cs3500.pa05.view.TaskViewImpl;
import cs3500.pa05.view.View;
import cs3500.pa05.view.WeekViewImpl;
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
    Controller taskController = new TaskControllerImpl();
//    View view = new WeekViewImpl(controller);
//    stage.setTitle("Journal");

    View view = new TaskViewImpl(taskController);
    stage.setTitle("Task");

    try {
      // load and place the view's scene onto the stage
      stage.setScene(view.load());

      // render the stage
      stage.show();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
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
