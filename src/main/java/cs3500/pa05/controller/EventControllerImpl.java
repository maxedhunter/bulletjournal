package cs3500.pa05.controller;

import static cs3500.pa05.model.Time.stringToTime;

import cs3500.pa05.model.DayEnum;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Time;
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
  private DayEnum dayEnum;
  @FXML
  private TextField nameField;
  private String name;
  @FXML
  private TextField descField;
  private String description = "";
  @FXML
  private TextField startField;
  private Time startTime;

  @FXML
  private TextField durationField;
  private int duration;

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
      this.dayEnum = DayEnum.valueOf(this.dayField.getText().toUpperCase());
    } catch (IllegalArgumentException e) {
      showDayWarning();
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.close();
    }
    this.name = this.nameField.getText();
    this.description = this.descField.getText();

    try {
      this.startTime = stringToTime(this.startField.getText());
    } catch (Exception e) {
      showTimeWarning();
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.close();
    }

    try {
      this.duration = Integer.parseInt(this.durationField.getText());
    } catch (NumberFormatException e) {
      showDurationWarning();
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.close();
    }

    Event submittedEvent = new Event(this.name, this.description, this.dayEnum,
        this.startTime, this.duration);

    eventCreated = submittedEvent;
    Stage stage = (Stage) submit.getScene().getWindow();
    stage.close();
  }

  public void showDayWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for day");
    alert.showAndWait();
  }

  public void showTimeWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for time");
    alert.showAndWait();
  }

  public void showDurationWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Warning");
    alert.setContentText("Invalid input for time");
    alert.showAndWait();
  }

  public Event getEventCreated() {
    return this.eventCreated;
  }
}
