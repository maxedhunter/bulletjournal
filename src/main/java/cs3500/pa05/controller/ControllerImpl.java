package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.EventViewImpl;
import cs3500.pa05.view.TaskViewImpl;
import cs3500.pa05.view.View;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the controller for a game of Whack-a-Mole.
 */
public class ControllerImpl implements Controller {

  private List<Task> tasksList = new ArrayList<>();
  private List<Event> eventsList = new ArrayList<>();
  private List<Task> mondayTasks = new ArrayList<>();
  private List<Task> tuesdayTasks = new ArrayList<>();
  private List<Task> wednesdayTasks = new ArrayList<>();
  private List<Task> thursdayTasks = new ArrayList<>();
  private List<Task> fridayTasks = new ArrayList<>();
  private List<Task> saturdayTasks = new ArrayList<>();
  private List<Task> sundayTasks = new ArrayList<>();

  private List<Task> completedTasksList = new ArrayList<>();
  private List<Task> mondayCompletedTasks = new ArrayList<>();
  private List<Task> tuesdayCompletedTasks = new ArrayList<>();
  private List<Task> wednesdayCompletedTasks = new ArrayList<>();
  private List<Task> thursdayCompletedTasks = new ArrayList<>();
  private List<Task> fridayCompletedTasks = new ArrayList<>();
  private List<Task> saturdayCompletedTasks = new ArrayList<>();
  private List<Task> sundayCompletedTasks = new ArrayList<>();

  private List<Event> mondayEvents = new ArrayList<>();
  private List<Event> tuesdayEvents = new ArrayList<>();
  private List<Event> wednesdayEvents = new ArrayList<>();
  private List<Event> thursdayEvents = new ArrayList<>();
  private List<Event> fridayEvents = new ArrayList<>();
  private List<Event> saturdayEvents = new ArrayList<>();
  private List<Event> sundayEvents = new ArrayList<>();
  @FXML
  private HBox weekdays;
  @FXML
  private VBox tasks;
  @FXML
  private VBox sunday;
  @FXML
  private VBox monday;
  @FXML
  private VBox tuesday;
  @FXML
  private VBox wednesday;
  @FXML
  private VBox thursday;
  @FXML
  private VBox friday;
  @FXML
  private VBox saturday;
  @FXML
  private MenuItem taskButton;
  @FXML
  private MenuItem eventButton;
  @FXML
  private TextField maximumTasks;
  @FXML
  private TextField maximumEvents;
  @FXML
  private Label totalTaskCount;
  @FXML
  private Label totalEventCount;
  int maxTask = 1000;
  int maxEvent = 1000;

  @FXML
  private ProgressBar sundayProgress;
  @FXML
  private ProgressBar mondayProgress;
  @FXML
  private ProgressBar tuesdayProgress;
  @FXML
  private ProgressBar wednesdayProgress;
  @FXML
  private ProgressBar thursdayProgress;
  @FXML
  private ProgressBar fridayProgress;
  @FXML
  private ProgressBar saturdayProgress;

  @FXML
  private Label weeklyPercentage;


  TaskControllerImpl taskController;
  EventControllerImpl eventController;

  public ControllerImpl(TaskControllerImpl taskController, EventControllerImpl eventController) {
    this.taskController = taskController;
    this.eventController = eventController;
  }

  public void handleCreateNewTask() {
    taskButton.setOnAction(event -> showTaskPage());
    taskButton.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
  }

  public void handleCreateNewEvent() {
    eventButton.setOnAction(event -> showEventPage());
    eventButton.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
  }

  public void addTaskQueue(Task task) {
    Button taskQButton = new Button(task.getName() + " " + task.getCompletion());
    tasks.getChildren().add(taskQButton);
  }

  public void removeFromQueue(VBox task) {
    Button toBeRemoved = null;
    for (Node child : tasks.getChildren()) {
      if (child instanceof Button) {
        Button button = (Button) child;
        if (button.getText().contains(task.getId())) {
          toBeRemoved = button;
          break;
        }
      }
    }
    if (toBeRemoved != null) {
      tasks.getChildren().remove(toBeRemoved);
    }
  }

  public void creatTaskButton() {
    Task task = taskController.getTaskCreated();
    Button taskButton = new Button(task.getName());
    VBox taskDetails = new VBox();
    Label description = new Label(task.getDescription());
    Label completion = new Label(task.getCompletion());
    ContextMenu contextMenu = new ContextMenu();
    MenuItem removeButton = new MenuItem("Remove Task");
    removeButton.setOnAction(event -> removeTask(task, taskDetails));
    removeButton.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
    MenuItem completeButton = new MenuItem("Set Complete");
    completeButton.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
    completeButton.setOnAction(event -> setCompletion(task, completion));
    contextMenu.getItems().addAll(removeButton, completeButton);
    taskButton.setContextMenu(contextMenu);
    taskDetails.getChildren().addAll(taskButton, description, completion);
    taskDetails.setId(task.getName());
    addTask(task, taskDetails);
  }

  public void creatEventButton() {
    Event newEvent = eventController.getEventCreated();
    Button eventButton = new Button(newEvent.getName());
    VBox eventDetails = new VBox();
    Label description = new Label(newEvent.getDescription());
    Label startTime = new Label(newEvent.getStartTime());
    Label duration = new Label(newEvent.getDuration());
    eventDetails.getChildren().addAll(eventButton, description, startTime, duration);
    ContextMenu contextMenu = new ContextMenu();
    MenuItem removeButton = new MenuItem("Remove Event");
    removeButton.setOnAction(event -> removeEvent(newEvent, eventDetails));
    removeButton.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
    contextMenu.getItems().add(removeButton);
    eventButton.setContextMenu(contextMenu);
    addEvent(newEvent, eventDetails);
  }

  public void addTask(Task task, VBox taskDetails) {
    if (task.getDay() == Day.MONDAY) {
      monday.getChildren().add(taskDetails);
      mondayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == Day.TUESDAY) {
      tuesday.getChildren().add(taskDetails);
      tuesdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == Day.WEDNESDAY) {
      wednesday.getChildren().add(taskDetails);
      wednesdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == Day.THURSDAY) {
      thursday.getChildren().add(taskDetails);
      thursdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == Day.FRIDAY) {
      friday.getChildren().add(taskDetails);
      fridayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == Day.SATURDAY) {
      saturday.getChildren().add(taskDetails);
      saturdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == Day.SUNDAY) {
      sunday.getChildren().add(taskDetails);
      sundayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    updateProgress(task);
    updateWeeklyProgress();
  }

  public void addEvent(Event newEvent, VBox eventDetails) {
    if (newEvent.getDay() == Day.MONDAY) {
      monday.getChildren().add(eventDetails);
      mondayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == Day.TUESDAY) {
      tuesday.getChildren().add(eventDetails);
      tuesdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == Day.WEDNESDAY) {
      wednesday.getChildren().add(eventDetails);
      wednesdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == Day.THURSDAY) {
      thursday.getChildren().add(eventDetails);
      thursdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == Day.FRIDAY) {
      friday.getChildren().add(eventDetails);
      fridayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == Day.SATURDAY) {
      saturday.getChildren().add(eventDetails);
      saturdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == Day.SUNDAY) {
      sunday.getChildren().add(eventDetails);
      sundayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
  }

  private void showTaskPage() {
    checkTaskCommitment();
    View taskView = new TaskViewImpl(taskController);
    Stage popupStage = new Stage();
    popupStage.setScene(taskView.load());
    popupStage.showAndWait();
    taskController.run();
    creatTaskButton();
  }

  private void showEventPage() {
    checkEventCommitment();
    View eventView = new EventViewImpl(eventController);
    Stage popupStage = new Stage();
    popupStage.setScene(eventView.load());
    popupStage.showAndWait();
    eventController.run();
    creatEventButton();
  }

  public void checkTaskCommitment() {
    if (!(maximumTasks.getText().equals(""))) {
      maxTask = Integer.parseInt(maximumTasks.getText()) - 1;
    }
    if (mondayTasks.size() >= maxTask || tuesdayTasks.size() >= maxTask
        || wednesdayTasks.size() >= maxTask || thursdayTasks.size() >= maxTask
        || fridayTasks.size() >= maxTask || saturdayTasks.size() >= maxTask
        || sundayTasks.size() >= maxTask) {
      taskButton.setOnAction(event -> showCommitmentWarning());
    } else {
      handleCreateNewTask();
    }
  }

  public void checkEventCommitment() {
    if (!(maximumEvents.getText().equals(""))) {
      maxEvent = Integer.parseInt(maximumEvents.getText()) - 1;
    }
    if (mondayEvents.size() >= maxEvent || tuesdayEvents.size() >= maxEvent
        || wednesdayEvents.size() >= maxEvent || thursdayEvents.size() >= maxEvent
        || fridayEvents.size() >= maxEvent || saturdayEvents.size() >= maxEvent
        || sundayEvents.size() >= maxEvent) {
      eventButton.setOnAction(event -> showCommitmentWarning());
    } else {
      handleCreateNewEvent();
    }
  }

  public void showCommitmentWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Commitment Warning");
    alert.setContentText("You cannot create more");
    alert.showAndWait();
  }

  public void removeTask(Task task, VBox taskBox) {
    VBox parent = (VBox) taskBox.getParent();
    parent.getChildren().remove(taskBox);
    tasksList.remove(task);
    completedTasksList.remove(task);
    updateTotalTask(tasksList);
    if (parent.getId().equals("monday")) {
      mondayTasks.remove(task);
      mondayCompletedTasks.remove(task);
    }
    if (parent.getId().equals("tuesday")) {
      tuesdayTasks.remove(task);
      tuesdayCompletedTasks.remove(task);
    }
    if (parent.getId().equals("wednesday")) {
      wednesdayTasks.remove(task);
      wednesdayCompletedTasks.remove(task);
    }
    if (parent.getId().equals("thursday")) {
      thursdayTasks.remove(task);
      thursdayCompletedTasks.remove(task);
    }
    if (parent.getId().equals("friday")) {
      fridayTasks.remove(task);
      fridayCompletedTasks.remove(task);
    }
    if (parent.getId().equals("saturday")) {
      saturdayTasks.remove(task);
      saturdayCompletedTasks.remove(task);
    }
    if (parent.getId().equals("sunday")) {
      sundayTasks.remove(task);
      sundayCompletedTasks.remove(task);
    }
    removeFromQueue(taskBox);
    updateProgress(task);
    updateWeeklyProgress();
  }

  public void removeEvent(Event removedEvent, VBox eventBox) {
    VBox parent = (VBox) eventBox.getParent();
    parent.getChildren().remove(eventBox);
    eventsList.remove(removedEvent);
    updateTotalEvent(eventsList);
    if (parent.getId().equals("monday")) {
      mondayEvents.remove(removedEvent);
    }
    if (parent.getId().equals("tuesday")) {
      tuesdayEvents.remove(removedEvent);
    }
    if (parent.getId().equals("wednesday")) {
      wednesdayEvents.remove(removedEvent);
    }
    if (parent.getId().equals("thursday")) {
      thursdayEvents.remove(removedEvent);
    }
    if (parent.getId().equals("friday")) {
      fridayEvents.remove(removedEvent);
    }
    if (parent.getId().equals("saturday")) {
      saturdayEvents.remove(removedEvent);
    }
    if (parent.getId().equals("sunday")) {
      sundayEvents.remove(removedEvent);
    }
  }

  public void updateTotalEvent(List<Event> events) {
    totalEventCount.setText(String.valueOf(events.size()));
  }

  public void updateTotalTask(List<Task> tasks) {
    totalTaskCount.setText(String.valueOf(tasks.size()));
  }

  public void setCompletion(Task task, Label completion) {
    task.setCompletion();
    completion.setText(task.getCompletion());
    setCompletionInQueue(task);
    completedTasksList.add(task);
    if (task.getDay() == Day.MONDAY) {
      mondayCompletedTasks.add(task);
    }
    if (task.getDay() == Day.TUESDAY) {
      tuesdayCompletedTasks.add(task);
    }
    if (task.getDay() == Day.WEDNESDAY) {
      wednesdayCompletedTasks.add(task);
    }
    if (task.getDay() == Day.THURSDAY) {
      thursdayCompletedTasks.add(task);
    }
    if (task.getDay() == Day.FRIDAY) {
      fridayCompletedTasks.add(task);
    }
    if (task.getDay() == Day.SATURDAY) {
      saturdayCompletedTasks.add(task);
    }
    if (task.getDay() == Day.SUNDAY) {
      sundayCompletedTasks.add(task);
    }
    updateProgress(task);
    updateWeeklyProgress();
  }

  public void updateProgress(Task task) {
    if (task.getDay() == Day.MONDAY) {
      mondayProgress.setProgress((double) mondayCompletedTasks.size() / mondayTasks.size());
    }
    if (task.getDay() == Day.TUESDAY) {
      tuesdayProgress.setProgress((double) tuesdayCompletedTasks.size() / tuesdayTasks.size());
    }
    if (task.getDay() == Day.WEDNESDAY) {
      wednesdayProgress.setProgress(
          (double) wednesdayCompletedTasks.size() / wednesdayTasks.size());
    }
    if (task.getDay() == Day.THURSDAY) {
      thursdayProgress.setProgress((double) thursdayCompletedTasks.size() / thursdayTasks.size());
    }
    if (task.getDay() == Day.FRIDAY) {
      fridayProgress.setProgress((double) fridayCompletedTasks.size() / fridayTasks.size());
    }
    if (task.getDay() == Day.SATURDAY) {
      saturdayProgress.setProgress((double) saturdayCompletedTasks.size() / saturdayTasks.size());
    }
    if (task.getDay() == Day.SUNDAY) {
      sundayProgress.setProgress((double) sundayCompletedTasks.size() / sundayTasks.size());
    }
  }

  public void updateWeeklyProgress() {
    double percentage = (double) completedTasksList.size() / tasksList.size();
    weeklyPercentage.setText(Math.round(percentage * 100.0) + "%");
  }

  public void setCompletionInQueue(Task task) {
    for (Node child : tasks.getChildren()) {
      if (child instanceof Button) {
        Button button = (Button) child;
        if (button.getText().contains(task.getName())) {
          button.setText(task.getName() + " " + task.getCompletion());
          break;
        }
      }
    }
  }


  /**
   * Initializes a game of Whack-a-Mole.
   *
   * @throws IllegalStateException if the game board is not defined
   */
  @FXML
  public void run() throws IllegalStateException {
    checkTaskCommitment();
    checkEventCommitment();
  }
}
