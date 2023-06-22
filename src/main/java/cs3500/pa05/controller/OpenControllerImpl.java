package cs3500.pa05.controller;

import cs3500.pa05.view.NewWeekViewImpl;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Represents a controller for opening a .bujo file
 */
public class OpenControllerImpl implements Controller {
  private static final String SOURCE_DIRECTORY = "src/test/resources/";

  @FXML
  private TextField openWeekField;

  private String openWeekFile;

  @FXML
  private Button submit;

  /**
   * Run method
   *
   * @throws IllegalStateException if running fails
   */
  @FXML
  public void run() throws IllegalStateException {
  }

  NewWeekViewImpl view = new NewWeekViewImpl(this);

  /**
   * Initializes the buttons
   */
  public void initialize() {
    submit.setOnAction(event -> submit());
  }

  /**
   * Handles user hitting the submit button
   */
  public void submit() {
    try {
      this.openWeekFile = this.openWeekField.getText();
    } catch (IllegalArgumentException e) {
      warning();
    }

    File file = new File(SOURCE_DIRECTORY + this.openWeekFile);

    // shows warning if the file does not end in .bujo and if it doesn't exist
    if ((!(openWeekFile.endsWith(".bujo"))) || !file.exists()) {
      warning();
    }

    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  private void warning() {
    view.showWarning();
    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  /**
   * Returns the file string.
   *
   * @return the open week file string
   */
  public String getOpenWeekFile() {
    return this.openWeekFile;
  }
}
