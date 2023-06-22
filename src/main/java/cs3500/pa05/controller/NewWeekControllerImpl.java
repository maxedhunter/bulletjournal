package cs3500.pa05.controller;

import cs3500.pa05.view.NewWeekViewImpl;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Represents a controller for making a new week.
 */
public class NewWeekControllerImpl implements Controller {
  private static final String SOURCE_DIRECTORY = "src/test/resources/";

  @FXML
  private TextField newWeekField;

  private String newWeekFile;

  @FXML
  private Button submit;

  /**
   * FXML run method
   *
   * @throws IllegalStateException if running fails
   */
  @FXML
  public void run() throws IllegalStateException {
  }

  NewWeekViewImpl view = new NewWeekViewImpl(this);

  /**
   * Initializes the submit button
   */
  public void initialize() {
    submit.setOnAction(event -> submit());
  }

  /**
   * Handles user hitting the submit button
   */
  public void submit() {
    try {
      this.newWeekFile = this.newWeekField.getText();
    } catch (IllegalArgumentException e) {
      warning();
    }

    File file = new File(SOURCE_DIRECTORY + this.newWeekFile);

    // shows warning if the file does not end in .bujo
    if ((!(newWeekFile.endsWith(".bujo"))) || file.exists()) {
      warning();
    }

    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  /**
   * Provides a warning for the user
   */
  private void warning() {
    view.showWarning();
    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  /**
   * Returns a string representation of a new week file
   *
   * @return the new file path
   */
  public String getNewWeekFile() {
    return this.newWeekFile;
  }
}
