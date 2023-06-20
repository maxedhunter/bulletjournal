package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.TaskViewImpl;
import cs3500.pa05.view.View;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the controller for a game of Whack-a-Mole.
 */
public class ControllerImpl implements Controller {

  private Stage stage;
  private Scene scene;
  private List<Task> tasksList = new ArrayList<>();
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
  private ProgressBar mondayProgress;

  TaskControllerImpl taskController;

  public ControllerImpl(TaskControllerImpl taskController) {
    this.taskController = taskController;
  }

  public void handleCreateNewTask() {
    taskButton.setOnAction(event -> showPopupWindow());
  }

  public void addTaskQueue(Task task) {
    Button taskQButton = new Button(task.getName()+ " " + task.getCompletion());
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
    tasksList.add(task);
    addTaskQueue(task);
    Button taskButton = new Button(task.getName());
    VBox taskDetails = new VBox();
    Label description = new Label(task.getDescription());
    Label completion = new Label(task.getCompletion());
    taskDetails.getChildren().addAll(taskButton, description, completion);
    taskDetails.setId(task.getName());
    ContextMenu contextMenu = new ContextMenu();
    MenuItem removeButton = new MenuItem("Remove Task");
    removeButton.setOnAction(event -> removeTask(taskDetails));
    MenuItem completeButton = new MenuItem("Set Complete");
    contextMenu.getItems().addAll(removeButton, completeButton);
    taskButton.setContextMenu(contextMenu);
    addTask(task, taskDetails);
  }

  public void addTask(Task task, VBox taskDetails){
    if(task.getDay() == Day.MONDAY) {
      monday.getChildren().add(taskDetails);
    } if (task.getDay() == Day.TUESDAY) {
      tuesday.getChildren().add(taskDetails);
    } if (task.getDay() == Day.WEDNESDAY) {
      wednesday.getChildren().add(taskDetails);
    } if (task.getDay() == Day.THURSDAY) {
      thursday.getChildren().add(taskDetails);
    } if (task.getDay() == Day.FRIDAY) {
      friday.getChildren().add(taskDetails);
    } if (task.getDay() == Day.SATURDAY) {
      saturday.getChildren().add(taskDetails);
    } if (task.getDay() == Day.SUNDAY) {
      sunday.getChildren().add(taskDetails);
    }
  }

  private void showPopupWindow() {
    View taskView = new TaskViewImpl(taskController);
    Stage popupStage = new Stage();
    popupStage.setScene(taskView.load());
    popupStage.showAndWait();
    taskController.run();
    creatTaskButton();
  }

  public void removeTask(VBox task) {
    VBox parent = (VBox) task.getParent();
    parent.getChildren().remove(task);
    removeFromQueue(task);
  }


  /**
   * Initializes a game of Whack-a-Mole.
   *
   * @throws IllegalStateException if the game board is not defined
   */
  @FXML
  public void run() throws IllegalStateException {
    //taskQueue();
    handleCreateNewTask();
  }
}
