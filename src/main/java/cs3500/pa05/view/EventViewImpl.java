package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

/**
 * Represents a simple event creation GUI view.
 */
public class EventViewImpl implements View {

  private final FXMLLoader loader;

  /**
   * Initializes a controller for event view.
   *
   * @param controller a controller
   */
  public EventViewImpl(Controller controller) {
    // look up and store the layout
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("Event.fxml"));
    this.loader.setController(controller);
  }

  /**
   * Shows a warning if an invalid day input is received
   */
  public void showDayWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for day");
    alert.showAndWait();
  }

  /**
   * Shows a warning if an invalid time input is received
   */
  public void showTimeWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for time");
    alert.showAndWait();
  }

  /**
   * Shows a warning if an invalid duration input is received
   */
  public void showDurationWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for duration");
    alert.showAndWait();
  }

  /**
   * Loads a scene for event creation in GUI layout.
   *
   * @return the layout
   */
  @Override
  public Scene load() throws IllegalStateException {
    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
