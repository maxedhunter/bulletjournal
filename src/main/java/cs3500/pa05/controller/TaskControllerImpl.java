package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Task;
import java.util.ArrayList;
import java.util.List;
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
  private Day day;
  @FXML
  private TextField nameField;
  private String name;
  private Stage stage = null;
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
      this.day = Day.valueOf(this.dayField.getText().toUpperCase());
    } catch (IllegalArgumentException e) {
      System.err.println("Not a valid weekday");
    }
    this.name = this.nameField.getText();
    this.description = this.descField.getText();

    Task submittedTask = new Task(this.name, this.description, this.day, false);
    System.out.print(submittedTask.getName());
    System.out.print(submittedTask.getDescription());
    System.out.print(submittedTask.getDay());
    System.out.print(submittedTask.getCompletion());
    taskCreated = submittedTask;
    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  public Task getTaskCreated() {
    return this.taskCreated;
  }
}
