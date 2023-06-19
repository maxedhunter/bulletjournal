package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
  @FXML
  private TextField descField;
  private String description;
  @FXML
  private Button submit;

  @Override
  public void run() throws IllegalStateException {
    submit.setOnAction(event -> submit());
  }


  /**
   * Handles user hitting the submit button
   */
  public void submit() {
    this.day = Day.valueOf(this.dayField.getText());
    this.name = this.nameField.getText();
    this.description = this.descField.getText();

    System.out.println(day + name + description);
  }
}
