package cs3500.pa05.controller;

import cs3500.pa05.model.DayEnum;
import cs3500.pa05.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Represents a task controller.
 */
public class TaskControllerImpl implements Controller {

  @FXML
  private TextField dayField;
  private DayEnum dayEnum;
  @FXML
  private TextField nameField;
  private String name;
  @FXML
  private TextField descField;
  private String description = "";
  @FXML
  private Button submit;

  private Task taskCreated;

  @FXML
  public void run() throws IllegalStateException {
  }

  public void initialize() {
    submit.setOnAction(event -> submit());
  }


  /**
   * Handles user hitting the submit button
   */
  public void submit() {
    try {
      this.dayEnum = DayEnum.valueOf(this.dayField.getText().toUpperCase());
    } catch (IllegalArgumentException e) {
      showWarning();
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.close();
    }
    this.name = this.nameField.getText();
    this.description = this.descField.getText();

    Task submittedTask = new Task(this.name, this.description, this.dayEnum, false);
    taskCreated = submittedTask;
    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  public void showWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for day");
    alert.showAndWait();
  }

  public Task getTaskCreated() {
    return this.taskCreated;
  }
}
