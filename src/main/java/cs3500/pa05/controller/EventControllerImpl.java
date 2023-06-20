package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Represents a task controller.
 */
public class EventControllerImpl implements Controller {
  @FXML
  private TextField dayField;
  private Day day;
  @FXML
  private TextField nameField;
  private String name;
  @FXML
  private TextField descField;
  private String description = "";
  @FXML
  private TextField startField;
  private String startTime;

  @FXML
  private TextField durationField;
  private String duration;

  @FXML
  private Button submit;

  private Event eventCreated;

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
      showWarning();
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.close();
    }
    this.name = this.nameField.getText();
    this.description = this.descField.getText();
    this.startTime = this.startField.getText();
    this.duration = this.durationField.getText();

    Event submittedEvent = new Event(this.name, this.description, this.day,
        this.startTime, this.duration);

    eventCreated = submittedEvent;
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

  public Event getEventCreated() {
    return this.eventCreated;
  }
}
