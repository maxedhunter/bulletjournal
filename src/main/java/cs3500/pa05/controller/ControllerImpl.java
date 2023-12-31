package cs3500.pa05.controller;

import static cs3500.pa05.model.StringUtils.parseLinks;
import static cs3500.pa05.model.Time.stringToTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.BujoReader;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Represents the controller for a bullet journal.
 */
public class ControllerImpl implements Controller {
  private static final String PATH = "src/test/resources/";
  private Map<DayEnum, Day> weekSum = new HashMap<>();
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
  private int maxTask = 1000;
  private int maxEvent = 1000;

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

  @FXML
  private Label sundayRemainingTask;
  @FXML
  private Label mondayRemainingTask;
  @FXML
  private Label tuesdayRemainingTask;
  @FXML
  private Label wednesdayRemainingTask;
  @FXML
  private Label thursdayRemainingTask;
  @FXML
  private Label fridayRemainingTask;
  @FXML
  private Label saturdayRemainingTask;

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
   * @param taskController    represents a task controller
   * @param eventController   represents an event controller
   * @param newWeekController represents a new week controller
   * @param openController    represents an open controller
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
    BujoReader reader = new BujoReader(PATH + fileName);
    loadFile(reader.read());
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
    maximumTasks.setText(String.valueOf(week.getMaxTasks()));
    maximumEvents.setText(String.valueOf(week.getMaxEvents()));

    for (Task task : week.getTasks()) {
      creatTaskButton(task);
    }

    for (Event event : week.getEvents()) {
      creatEventButton(event);
    }

    mondayCompletedTasks.addAll(week.getDays().get(DayEnum.MONDAY).getCompletedTasks());

    tuesdayCompletedTasks.addAll(week.getDays().get(DayEnum.TUESDAY).getCompletedTasks());

    wednesdayCompletedTasks.addAll(week.getDays().get(DayEnum.WEDNESDAY).getCompletedTasks());

    thursdayCompletedTasks.addAll(week.getDays().get(DayEnum.THURSDAY).getCompletedTasks());

    fridayCompletedTasks.addAll(week.getDays().get(DayEnum.FRIDAY).getCompletedTasks());

    saturdayCompletedTasks.addAll(week.getDays().get(DayEnum.SATURDAY).getCompletedTasks());

    sundayCompletedTasks.addAll(week.getDays().get(DayEnum.SUNDAY).getCompletedTasks());

    for (Task task : tasksList) {
      if (task.getCompletion()) {
        completedTasksList.add(task);
      }
    }

    for (Task task : completedTasksList) {
      updateProgress(task);
    }

    updateWeeklyProgress();
    updateTotalTask(tasksList);
    updateTotalEvent(eventsList);
  }

  /**
   * Resets all the fields.
   */
  public void reset() {
    this.weekSum = new HashMap<>();
    this.tasksList = new ArrayList<>();
    this.eventsList = new ArrayList<>();

    this.mondayTasks = new ArrayList<>();
    this.tuesdayTasks = new ArrayList<>();
    this.wednesdayTasks = new ArrayList<>();
    this.thursdayTasks = new ArrayList<>();
    this.fridayTasks = new ArrayList<>();
    this.saturdayTasks = new ArrayList<>();
    this.sundayTasks = new ArrayList<>();

    this.completedTasksList = new ArrayList<>();

    this.mondayCompletedTasks = new ArrayList<>();
    this.tuesdayCompletedTasks = new ArrayList<>();
    this.wednesdayCompletedTasks = new ArrayList<>();
    this.thursdayCompletedTasks = new ArrayList<>();
    this.fridayCompletedTasks = new ArrayList<>();
    this.saturdayCompletedTasks = new ArrayList<>();
    this.sundayCompletedTasks = new ArrayList<>();

    this.mondayEvents = new ArrayList<>();
    this.tuesdayEvents = new ArrayList<>();
    this.wednesdayEvents = new ArrayList<>();
    this.thursdayEvents = new ArrayList<>();
    this.fridayEvents = new ArrayList<>();
    this.saturdayEvents = new ArrayList<>();
    this.sundayEvents = new ArrayList<>();
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
  public void creatTaskButton(Task task) {
    VBox taskDetails = new VBox();

    MenuItem removeButton = new MenuItem("Remove task");
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
    TextFlow description = updateUrls(task.getDescription());
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
  public void creatEventButton(Event newEvent) {
    VBox eventDetails = new VBox();

    MenuItem removeButton = new MenuItem("Remove Event");
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
    TextFlow description = updateUrls(newEvent.getDescription());
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
   * @param task        to be added
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
   * @param newEvent     to be added
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
    Task task = taskController.getTaskCreated();
    creatTaskButton(task);
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
   * @param task    to be removed
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
   * @param eventBox     that has the event
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
   * @param task       to be updated
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
   * @param task        to be updated
   * @param taskButton  button to be updated
   * @param taskDetails vbox to be updated
   * @param name        new name
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
   * @param task            to be updated
   * @param taskDescription text flow to be updated
   * @param description     new description
   */
  public void changeTaskDescription(Task task, TextFlow taskDescription, String description) {
    taskDescription = updateUrls(description);
    task.setDescription(description);
  }

  /**
   * Updates a label to have urls
   *
   * @param string to add urls to
   */
  private TextFlow updateUrls(String string) {
    List<String> urls = parseLinks(string);

    String newContent = string;
    TextFlow textFlow = new TextFlow();

    List<String> parts = new ArrayList<>();
    int currentIndex = 0;
    for (String url : urls) {
      int urlIndex = newContent.indexOf(url, currentIndex);
      if (urlIndex >= 0) {
        String part = newContent.substring(currentIndex, urlIndex);
        parts.add(part);

        // Add the URL as a Hyperlink
        Hyperlink hyperlink = new Hyperlink(url);
        hyperlink.setOnAction(e -> {
          try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
          } catch (java.io.IOException | java.net.URISyntaxException ex) {
            throw new IllegalArgumentException("Unable to open URL");
          }
        });

        textFlow.getChildren().add(hyperlink);
        currentIndex = urlIndex + url.length();
      }
    }

    if (currentIndex < newContent.length()) {
      String part = newContent.substring(currentIndex);
      parts.add(part);
    }

    for (String part : parts) {
      Text text = new Text(part);
      textFlow.getChildren().add(text);
    }

    return textFlow;
  }

  /**
   * Changes the event name
   *
   * @param event       to be updated
   * @param eventButton to be updated
   * @param name        new event name
   */
  public void changeEventName(Event event, Button eventButton, String name) {
    eventButton.setText(name);
    event.setName(name);
  }

  /**
   * Changes the event description
   *
   * @param event          to be updated
   * @param description    to be udpated
   * @param newDescription new event description
   */
  public void changeEventDescription(Event event, TextFlow description, String newDescription) {
    description = updateUrls(newDescription);
    event.setDescription(newDescription);
  }

  /**
   * Changes the event start time
   *
   * @param event     to be updated
   * @param startTime to be updated
   * @param newTime   new event start time
   */
  public void changeEventStartTime(Event event, Label startTime, Time newTime) {
    startTime.setText(String.valueOf(newTime));
    event.setStartTime(newTime);
  }

  /**
   * Changes the event duration
   *
   * @param event       to be updated
   * @param duration    to be updated
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
      mondayRemainingTask.setText(String.valueOf(mondayTasks.size()
          - mondayCompletedTasks.size()));
    }
    if (task.getDay() == DayEnum.TUESDAY) {
      tuesdayProgress.setProgress((double) tuesdayCompletedTasks.size() / tuesdayTasks.size());
      tuesdayRemainingTask.setText(String.valueOf(tuesdayTasks.size()
          - tuesdayCompletedTasks.size()));
    }
    if (task.getDay() == DayEnum.WEDNESDAY) {
      wednesdayProgress.setProgress(
          (double) wednesdayCompletedTasks.size() / wednesdayTasks.size());
      wednesdayRemainingTask.setText(String.valueOf(wednesdayTasks.size()
          - wednesdayCompletedTasks.size()));
    }
    if (task.getDay() == DayEnum.THURSDAY) {
      thursdayProgress.setProgress((double) thursdayCompletedTasks.size() / thursdayTasks.size());
      thursdayRemainingTask.setText(String.valueOf(thursdayTasks.size()
          - thursdayCompletedTasks.size()));
    }
    if (task.getDay() == DayEnum.FRIDAY) {
      fridayProgress.setProgress((double) fridayCompletedTasks.size() / fridayTasks.size());
      fridayRemainingTask.setText(String.valueOf(fridayTasks.size()
          - fridayCompletedTasks.size()));
    }
    if (task.getDay() == DayEnum.SATURDAY) {
      saturdayProgress.setProgress((double) saturdayCompletedTasks.size() / saturdayTasks.size());
      saturdayRemainingTask.setText(String.valueOf(saturdayTasks.size()
          - saturdayCompletedTasks.size()));
    }
    if (task.getDay() == DayEnum.SUNDAY) {
      sundayProgress.setProgress((double) sundayCompletedTasks.size() / sundayTasks.size());
      sundayRemainingTask.setText(String.valueOf(sundayTasks.size()
          - sundayCompletedTasks.size()));
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
    getWeekName();
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

    return createWeek(weekName, tasksJson, eventsJson, daysJson,
        maxTask, maxEvent);
  }

  /**
   * Creates the week (adapted from createMessage)
   */
  private JsonNode createWeek(String weekName, Record tasks, Record events,
                              Record days, int maxTasks, int maxEvents) {
    WeekJson week =
        new WeekJson(weekName,
            JsonUtils.serializeRecord(tasks, new ObjectMapper()),
            JsonUtils.serializeRecord(events, new ObjectMapper()),
            JsonUtils.serializeRecord(days, new ObjectMapper()),
            maxTasks, maxEvents);
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
