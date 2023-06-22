package cs3500.pa05.controller;

import cs3500.pa05.model.DayEnum;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.TaskViewImpl;
import javafx.fxml.FXML;
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
  @FXML
  private TextField descField;
  private String description = "";
  @FXML
  private Button submit;

  private Task taskCreated;

  /**
   * FXML run method
   *
   * @throws IllegalStateException if run fails
   */
  @FXML
  public void run() throws IllegalStateException {
  }

  /**
   * Initializes the submit button
   */
  public void initialize() {
    submit.setOnAction(event -> submit());
  }

  TaskViewImpl taskView = new TaskViewImpl(this);


  /**
   * Handles user hitting the submit button
   */
  public void submit() {
    try {
      this.dayEnum = DayEnum.valueOf(this.dayField.getText().toUpperCase());
    } catch (IllegalArgumentException e) {
      taskView.showWarning();
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.close();
    }
    String name = this.nameField.getText();
    this.description = this.descField.getText();

    taskCreated = new Task(name, this.description, this.dayEnum, false);
    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  /**
   * Returns the created task
   *
   * @return task
   */
  public Task getTaskCreated() {
    return this.taskCreated;
  }
}
