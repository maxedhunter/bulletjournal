package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

/**
 * Represents an open file view
 */
public class OpenFileViewImpl implements View {

  private final FXMLLoader loader;

  /**
   * Initializes a controller
   *
   * @param controller to initialize
   */
  public OpenFileViewImpl(Controller controller) {
    // look up and store the layout
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("OpenFile.fxml"));
    this.loader.setController(controller);
  }

  /**
   * Shows a warning if invalid file name is provided
   */
  public void showWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for file name");
    alert.showAndWait();
  }

  /**
   * Loads a scene for an open week implementation creation in GUI layout.
   *
   * @return the layout
   */
  @Override
  public Scene load() throws IllegalStateException {
    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout." + exc); //TODO remove exc
    }
  }
}
