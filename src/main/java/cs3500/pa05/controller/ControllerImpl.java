package cs3500.pa05.controller;

import static cs3500.pa05.model.Time.stringToTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BujoWriter;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.DayEnum;
import cs3500.pa05.model.DaysJson;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.EventsJson;
import cs3500.pa05.model.JsonUtils;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TasksJson;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.WeekJson;
import cs3500.pa05.view.EventViewImpl;
import cs3500.pa05.view.NewWeekViewImpl;
import cs3500.pa05.view.OpenFileViewImpl;
import cs3500.pa05.view.TaskViewImpl;
import cs3500.pa05.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the controller for a bullet journal.
 */
public class ControllerImpl implements Controller {
  private static final String PATH = "src/test/resources/";
  private final Map<DayEnum, Day> weekSum = new HashMap<>();
  private final List<Task> tasksList = new ArrayList<>();
  private final List<Event> eventsList = new ArrayList<>();

  private final List<Task> mondayTasks = new ArrayList<>();
  private final List<Task> tuesdayTasks = new ArrayList<>();
  private final List<Task> wednesdayTasks = new ArrayList<>();
  private final List<Task> thursdayTasks = new ArrayList<>();
  private final List<Task> fridayTasks = new ArrayList<>();
  private final List<Task> saturdayTasks = new ArrayList<>();
  private final List<Task> sundayTasks = new ArrayList<>();

  private final List<Task> completedTasksList = new ArrayList<>();

  private final List<Task> mondayCompletedTasks = new ArrayList<>();
  private final List<Task> tuesdayCompletedTasks = new ArrayList<>();
  private final List<Task> wednesdayCompletedTasks = new ArrayList<>();
  private final List<Task> thursdayCompletedTasks = new ArrayList<>();
  private final List<Task> fridayCompletedTasks = new ArrayList<>();
  private final List<Task> saturdayCompletedTasks = new ArrayList<>();
  private final List<Task> sundayCompletedTasks = new ArrayList<>();

  private final List<Event> mondayEvents = new ArrayList<>();
  private final List<Event> tuesdayEvents = new ArrayList<>();
  private final List<Event> wednesdayEvents = new ArrayList<>();
  private final List<Event> thursdayEvents = new ArrayList<>();
  private final List<Event> fridayEvents = new ArrayList<>();
  private final List<Event> saturdayEvents = new ArrayList<>();
  private final List<Event> sundayEvents = new ArrayList<>();

  // FXML fields
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

  // default task + event max
  int maxTask = 1000;
  int maxEvent = 1000;

  // progress bars
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
  @FXML
  private TextField weekNameField;
  @FXML
  private MenuItem openFileButton;
  @FXML
  private TextField filePath;
  @FXML
  private MenuItem saveFileButton;
  @FXML
  private MenuItem newWeekButton;

  private String weekName = "Week";
  private String fileName = "defaultOutput.bujo";

  // controllers

  private final TaskControllerImpl taskController;
  private final EventControllerImpl eventController;
  private final NewWeekControllerImpl newWeekController;
  private final OpenControllerImpl openController;

  /**
   * Initializes the main controllers with sub controllers.
   *
   * @param taskController  represents a task controller
   * @param eventController represents an event controller
   * @param newWeekController represents a new week controller
   * @param openController represents an open controller
   */
  public ControllerImpl(TaskControllerImpl taskController, EventControllerImpl eventController,
                        NewWeekControllerImpl newWeekController,
                        OpenControllerImpl openController) {
    this.taskController = taskController;
    this.eventController = eventController;
    this.newWeekController = newWeekController;
    this.openController = openController;
  }

  /**
   * Handles getting the week name
   */
  public void getWeekName() {
    weekName = weekNameField.getText();
  }

  /**
   * Handles saving the file
   */
  public void handleSaveFile() {
    saveFileButton.setOnAction(event -> saveToFile());
    saveFileButton.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
  }

  /**
   * Saves to a file using the Bujo file writer.
   */
  public void saveToFile() {
    BujoWriter writer = new BujoWriter(PATH + this.fileName);
    writer.writeToFile(sumUp(), new StringBuilder());
  }

  /**
   * Handles opening a file.
   */
  public void handleOpenFile() {
    openFileButton.setOnAction(event -> showOpenFile());
    openFileButton.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
  }

  /**
   * Shows the screen for opening a file.
   */
  public void showOpenFile() {
    View openFileView = new OpenFileViewImpl(openController);
    Stage popupStage = new Stage();
    popupStage.setScene(openFileView.load());
    popupStage.showAndWait();
    newWeekController.run();
    fileName = openController.getOpenWeekFile();
    //TODO load it?
  }

  /**
   * Handles creating a new file.
   */
  public void handleNewFile() {
    newWeekButton.setOnAction(event -> showNewFilePage());
    newWeekButton.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
  }

  /**
   * Shows creating a new file page.
   */
  public void showNewFilePage() {
    View newWeekView = new NewWeekViewImpl(newWeekController);
    Stage popupStage = new Stage();
    popupStage.setScene(newWeekView.load());
    popupStage.showAndWait();
    newWeekController.run();
    fileName = newWeekController.getNewWeekFile();
  }

  /**
   * Loads the given week file to the display.
   *
   * @param week to be loaded
   */
  public void loadFile(Week week) {
    reset();
    weekNameField.setText(week.getName());
    tasksList.addAll(week.getTasks());
    eventsList.addAll(week.getEvents());
    for (Task task: tasksList) {
      creatTaskButton(task);
    }

    for (Event event: eventsList) {
      creatEventButton(event);
    }
  }

  /**
   * Resets all the fields.
   */
  public void reset() {

  }

  /**
   * Handles creating a new task
   */
  public void handleCreateNewTask() {
    taskButton.setOnAction(event -> showTaskPage());
    taskButton.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
  }

  /**
   * Handles creating a new event
   */
  public void handleCreateNewEvent() {
    eventButton.setOnAction(event -> showEventPage());
    eventButton.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
  }

  /**
   * Creates a task in the task queue
   *
   * @param task to be added
   */
  public void addTaskQueue(Task task) {
    Button taskQueueButton = new Button(task.getName() + " " + task.getCompletionString());
    tasks.getChildren().add(taskQueueButton);
  }

  /**
   * Removes a task from the queue
   *
   * @param task to be removed
   */
  public void removeFromQueue(VBox task) {
    Button toBeRemoved = null;
    for (Node child : tasks.getChildren()) {
      if (child instanceof Button button) {
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

  /**
   * Sets up creating task.
   */
<<<<<<< Updated upstream
  public void createTaskButton() {
    Task task = taskController.getTaskCreated();

=======
<<<<<<< HEAD
  public void creatTaskButton(Task task) {
    Button taskButton = new Button(task.getName());
    VBox taskDetails = new VBox();
    Label description = new Label(task.getDescription());
    Label completion = new Label(task.getCompletionString());

    ContextMenu contextMenu = new ContextMenu();
=======
  public void createTaskButton() {
    Task task = taskController.getTaskCreated();

>>>>>>> 4141e0918c4bc3f235ea852227db0c1dd0bbe3b4
>>>>>>> Stashed changes
    MenuItem removeButton = new MenuItem("Remove task");
    VBox taskDetails = new VBox();
    removeButton.setOnAction(event -> removeTask(task, taskDetails));
    removeButton.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));

    MenuItem changeName = new MenuItem("Change name");
    TextField nameField = new TextField();
    changeName.setGraphic(nameField);
    Button taskButton = new Button(task.getName());
    changeName.setOnAction(
        event -> changeTaskName(task, taskButton, taskDetails, nameField.getText()));

    MenuItem changeDescription = new MenuItem("Change description");
    TextField descriptionField = new TextField();
    changeDescription.setGraphic(descriptionField);
    Label description = new Label(task.getDescription());
    changeDescription.setOnAction(
        event -> changeTaskDescription(task, description, descriptionField.getText()));

    MenuItem completeButton = new MenuItem("Set complete");
    completeButton.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
    Label completion = new Label(task.getCompletionString());
    completeButton.setOnAction(event -> setCompletion(task, completion));

    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().addAll(removeButton, completeButton, changeName, changeDescription);
    taskButton.setContextMenu(contextMenu);
    taskDetails.getChildren().addAll(taskButton, description, completion);
    taskDetails.setId(task.getName());
    addTask(task, taskDetails);
  }

  /**
   * Sets up creating an event
   */
<<<<<<< Updated upstream
  public void creatEventButton() {
    Event newEvent = eventController.getEventCreated();
=======
<<<<<<< HEAD
  public void creatEventButton(Event newEvent) {
    Button eventButton = new Button(newEvent.getName());
    VBox eventDetails = new VBox();
    Label description = new Label(newEvent.getDescription());
    Label startTime = new Label(newEvent.getStartTime().toString());
    Label duration = new Label(Integer.toString(newEvent.getDuration()));
=======
  public void creatEventButton() {
    Event newEvent = eventController.getEventCreated();
>>>>>>> 4141e0918c4bc3f235ea852227db0c1dd0bbe3b4
>>>>>>> Stashed changes

    MenuItem removeButton = new MenuItem("Remove Event");
    VBox eventDetails = new VBox();
    removeButton.setOnAction(event -> removeEvent(newEvent, eventDetails));
    removeButton.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));

    MenuItem changeName = new MenuItem("Change name");
    TextField nameField = new TextField();
    changeName.setGraphic(nameField);
    Button eventButton = new Button(newEvent.getName());
    changeName.setOnAction(
        event -> changeEventName(newEvent, eventButton, nameField.getText()));

    MenuItem changeDescription = new MenuItem("Change description");
    TextField descriptionField = new TextField();
    changeDescription.setGraphic(descriptionField);
    Label description = new Label(newEvent.getDescription());
    changeDescription.setOnAction(
        event -> changeEventDescription(newEvent, description, descriptionField.getText()));

    MenuItem changeStartTime = new MenuItem("Change start time");
    TextField startTimeField = new TextField();
    changeStartTime.setGraphic(startTimeField);
    Label startTime = new Label(newEvent.getStartTime().toString());
    changeStartTime.setOnAction(
        event -> changeEventStartTime(newEvent, startTime, stringToTime(startTimeField.getText())));

    MenuItem changeDuration = new MenuItem("Change duration");
    TextField durationField = new TextField();
    changeDuration.setGraphic(durationField);
    Label duration = new Label(Integer.toString(newEvent.getDuration()));
    changeDuration.setOnAction(
        event -> changeEventDuration(newEvent, duration,
            Integer.parseInt(durationField.getText())));

    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems()
        .addAll(removeButton, changeName, changeDescription, changeStartTime, changeDuration);
    eventButton.setContextMenu(contextMenu);
    eventDetails.getChildren().addAll(eventButton, description, startTime, duration);
    addEvent(newEvent, eventDetails);
  }

  /**
   * Adds a task
   *
   * @param task to be added
   * @param taskDetails the details to be added
   */
  public void addTask(Task task, VBox taskDetails) {
    if (task.getDay() == DayEnum.MONDAY) {
      monday.getChildren().add(taskDetails);
      mondayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == DayEnum.TUESDAY) {
      tuesday.getChildren().add(taskDetails);
      tuesdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == DayEnum.WEDNESDAY) {
      wednesday.getChildren().add(taskDetails);
      wednesdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == DayEnum.THURSDAY) {
      thursday.getChildren().add(taskDetails);
      thursdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == DayEnum.FRIDAY) {
      friday.getChildren().add(taskDetails);
      fridayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == DayEnum.SATURDAY) {
      saturday.getChildren().add(taskDetails);
      saturdayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    if (task.getDay() == DayEnum.SUNDAY) {
      sunday.getChildren().add(taskDetails);
      sundayTasks.add(task);
      tasksList.add(task);
      updateTotalTask(tasksList);
      addTaskQueue(task);
    }
    updateProgress(task);
    updateWeeklyProgress();
  }

  /**
   * Adds an event
   *
   * @param newEvent to be added
   * @param eventDetails details to be added
   */
  public void addEvent(Event newEvent, VBox eventDetails) {
    if (newEvent.getDay() == DayEnum.MONDAY) {
      monday.getChildren().add(eventDetails);
      mondayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == DayEnum.TUESDAY) {
      tuesday.getChildren().add(eventDetails);
      tuesdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == DayEnum.WEDNESDAY) {
      wednesday.getChildren().add(eventDetails);
      wednesdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == DayEnum.THURSDAY) {
      thursday.getChildren().add(eventDetails);
      thursdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == DayEnum.FRIDAY) {
      friday.getChildren().add(eventDetails);
      fridayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == DayEnum.SATURDAY) {
      saturday.getChildren().add(eventDetails);
      saturdayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
    if (newEvent.getDay() == DayEnum.SUNDAY) {
      sunday.getChildren().add(eventDetails);
      sundayEvents.add(newEvent);
      eventsList.add(newEvent);
      updateTotalEvent(eventsList);
    }
  }

  /**
   * Shows the task page
   */
  private void showTaskPage() {
    checkTaskCommitment();
    View taskView = new TaskViewImpl(taskController);
    Stage popupStage = new Stage();
    popupStage.setScene(taskView.load());
    popupStage.showAndWait();
    taskController.run();
<<<<<<< Updated upstream
    createTaskButton();
=======
<<<<<<< HEAD
    Task task = taskController.getTaskCreated();
    creatTaskButton(task);
=======
    createTaskButton();
>>>>>>> 4141e0918c4bc3f235ea852227db0c1dd0bbe3b4
>>>>>>> Stashed changes
  }

  /**
   * Shows the event page
   */
  private void showEventPage() {
    checkEventCommitment();
    View eventView = new EventViewImpl(eventController);
    Stage popupStage = new Stage();
    popupStage.setScene(eventView.load());
    popupStage.showAndWait();
    eventController.run();
    Event newEvent = eventController.getEventCreated();
    creatEventButton(newEvent);
  }

  /**
   * Checks task commitment, handles creating a new task
   * if less than max number of tasks
   */
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

  /**
   * Checks event commitment, handles creating a new event
   * if less than the number of max events
   */
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

  /**
   * Displays a commitment warning
   */
  public void showCommitmentWarning() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alert");
    alert.setHeaderText("Commitment Warning");
    alert.setContentText("You cannot create more");
    alert.showAndWait();
  }

  /**
   * Removes a task
   *
   * @param task to be removed
   * @param taskBox that holds the task
   */
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

  /**
   * Removes an event
   *
   * @param removedEvent to be removed
   * @param eventBox that has the event
   */
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

  /**
   * Updates the total events
   *
   * @param events to be updated to
   */
  public void updateTotalEvent(List<Event> events) {
    totalEventCount.setText(String.valueOf(events.size()));
  }

  /**
   * Updates the total tasks
   *
   * @param tasks to be updated to
   */
  public void updateTotalTask(List<Task> tasks) {
    totalTaskCount.setText(String.valueOf(tasks.size()));
  }

  /**
   * Sets a given task to completed
   *
   * @param task to be updated
   * @param completion label to be updated
   */
  public void setCompletion(Task task, Label completion) {
    task.setCompletion();
    completion.setText(task.getCompletionString());
    setCompletionInQueue(task);
    completedTasksList.add(task);
    if (task.getDay() == DayEnum.MONDAY) {
      mondayCompletedTasks.add(task);
    }
    if (task.getDay() == DayEnum.TUESDAY) {
      tuesdayCompletedTasks.add(task);
    }
    if (task.getDay() == DayEnum.WEDNESDAY) {
      wednesdayCompletedTasks.add(task);
    }
    if (task.getDay() == DayEnum.THURSDAY) {
      thursdayCompletedTasks.add(task);
    }
    if (task.getDay() == DayEnum.FRIDAY) {
      fridayCompletedTasks.add(task);
    }
    if (task.getDay() == DayEnum.SATURDAY) {
      saturdayCompletedTasks.add(task);
    }
    if (task.getDay() == DayEnum.SUNDAY) {
      sundayCompletedTasks.add(task);
    }
    updateProgress(task);
    updateWeeklyProgress();
  }

  /**
   * Changes the task name.
   *
   * @param task to be updated
   * @param taskButton button to be updated
   * @param taskDetails vbox to be updated
   * @param name new name
   */
  public void changeTaskName(Task task, Button taskButton, VBox taskDetails, String name) {
    taskButton.setText(name);
    taskDetails.setId(name);
    changeNameInQueue(task, name);
    task.setName(name);
  }

  /**
   * Changes the task description.
   *
   * @param task to be updated
   * @param taskDescription label to be updated
   * @param description new description
   */
  public void changeTaskDescription(Task task, Label taskDescription, String description) {
    taskDescription.setText(description);
    task.setDescription(description);
  }

  /**
   * Changes the event name
   *
   * @param event to be updated
   * @param eventButton to be updated
   * @param name new event name
   */
  public void changeEventName(Event event, Button eventButton, String name) {
    eventButton.setText(name);
    event.setName(name);
  }

  /**
   * Changes the event description
   *
   * @param event to be updated
   * @param description to be udpated
   * @param newDescription new event description
   */
  public void changeEventDescription(Event event, Label description, String newDescription) {
    description.setText(newDescription);
    event.setDescription(newDescription);
  }

  /**
   * Changes the event start time
   *
   * @param event to be updated
   * @param startTime to be updated
   * @param newTime new event start time
   */
  public void changeEventStartTime(Event event, Label startTime, Time newTime) {
    startTime.setText(String.valueOf(newTime));
    event.setStartTime(newTime);
  }

  /**
   * Changes the event duration
   *
   * @param event to be updated
   * @param duration to be updated
   * @param newDuration new event duration
   */
  public void changeEventDuration(Event event, Label duration, int newDuration) {
    duration.setText(String.valueOf(newDuration));
    event.setDuration(newDuration);
  }

  /**
   * Updates the progress bar
   *
   * @param task that updates progress
   */
  public void updateProgress(Task task) {
    if (task.getDay() == DayEnum.MONDAY) {
      mondayProgress.setProgress((double) mondayCompletedTasks.size() / mondayTasks.size());
    }
    if (task.getDay() == DayEnum.TUESDAY) {
      tuesdayProgress.setProgress((double) tuesdayCompletedTasks.size() / tuesdayTasks.size());
    }
    if (task.getDay() == DayEnum.WEDNESDAY) {
      wednesdayProgress.setProgress(
          (double) wednesdayCompletedTasks.size() / wednesdayTasks.size());
    }
    if (task.getDay() == DayEnum.THURSDAY) {
      thursdayProgress.setProgress((double) thursdayCompletedTasks.size() / thursdayTasks.size());
    }
    if (task.getDay() == DayEnum.FRIDAY) {
      fridayProgress.setProgress((double) fridayCompletedTasks.size() / fridayTasks.size());
    }
    if (task.getDay() == DayEnum.SATURDAY) {
      saturdayProgress.setProgress((double) saturdayCompletedTasks.size() / saturdayTasks.size());
    }
    if (task.getDay() == DayEnum.SUNDAY) {
      sundayProgress.setProgress((double) sundayCompletedTasks.size() / sundayTasks.size());
    }
  }

  /**
   * Updates the total weekly progress
   */
  public void updateWeeklyProgress() {
    double percentage = (double) completedTasksList.size() / tasksList.size();
    weeklyPercentage.setText(Math.round(percentage * 100.0) + "%");
  }

  /**
   * Updates completion in queue
   *
   * @param task to be updated
   */
  public void setCompletionInQueue(Task task) {
    for (Node child : tasks.getChildren()) {
      if (child instanceof Button button) {
        if (button.getText().contains(task.getName())) {
          button.setText(task.getName() + " " + task.getCompletionString());
          break;
        }
      }
    }
  }

  /**
   * Updates the name in queue
   *
   * @param task to be updated
   * @param name new name
   */
  public void changeNameInQueue(Task task, String name) {
    for (Node child : tasks.getChildren()) {
      if (child instanceof Button button) {
        if (button.getText().contains(task.getName())) {
          button.setText(name + " " + task.getCompletionString());
          break;
        }
      }
    }
  }

  /**
   * Returns the JSON.
   */
  private JsonNode sumUp() {
    Day mon = new Day(mondayTasks, mondayCompletedTasks, mondayEvents);
    weekSum.put(DayEnum.MONDAY, mon);
    Day tues = new Day(tuesdayTasks, tuesdayCompletedTasks, tuesdayEvents);
    weekSum.put(DayEnum.TUESDAY, tues);
    Day wed = new Day(wednesdayTasks, wednesdayCompletedTasks, wednesdayEvents);
    weekSum.put(DayEnum.WEDNESDAY, wed);
    Day thurs = new Day(thursdayTasks, thursdayCompletedTasks, thursdayEvents);
    weekSum.put(DayEnum.THURSDAY, thurs);
    Day fri = new Day(fridayTasks, fridayCompletedTasks, fridayEvents);
    weekSum.put(DayEnum.FRIDAY, fri);
    Day sat = new Day(saturdayTasks, saturdayCompletedTasks, saturdayEvents);
    weekSum.put(DayEnum.SATURDAY, sat);
    Day sun = new Day(sundayTasks, sundayCompletedTasks, sundayEvents);
    weekSum.put(DayEnum.SUNDAY, sun);

    DaysJson daysJson = new DaysJson(weekSum);
    TasksJson tasksJson = new TasksJson(tasksList);
    EventsJson eventsJson = new EventsJson(eventsList);

    JsonNode week = createWeek(weekName, tasksJson, eventsJson, daysJson);
    return week;
  }

  /**
   * Creates the week (adapted from createMessage)
   */
  private JsonNode createWeek(String weekName, Record tasks, Record events, Record days) {
    WeekJson week =
        new WeekJson(weekName,
            JsonUtils.serializeRecord(tasks, new ObjectMapper()),
            JsonUtils.serializeRecord(events, new ObjectMapper()),
            JsonUtils.serializeRecord(days, new ObjectMapper()));
    return JsonUtils.serializeRecord(week, new ObjectMapper());
  }

  /**
   * Initializes a bullet journal
   */
  @FXML
  public void run() {
    getWeekName();
    checkTaskCommitment();
    checkEventCommitment();
    handleNewFile();
    handleOpenFile();
    handleSaveFile();
  }
}
