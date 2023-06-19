package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.TaskViewImpl;
import cs3500.pa05.view.View;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the controller for a game of Whack-a-Mole.
 */
public class ControllerImpl implements Controller {

  private Stage stage;
  private Scene scene;
  @FXML
  private VBox tasks;
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

  public void taskQueue(){
    Task t1 = new Task("Task1", "first task", Day.MONDAY, false);
    Button task1 = new Button(t1.getName());
    tasks.getChildren().add(task1);
    //Creating a context menu
    ContextMenu contextMenu = new ContextMenu();
    //Creating the menu Items for the context menu
    MenuItem item1 = new MenuItem("Remove Task");
    MenuItem item2 = new MenuItem("Set Complete");
    contextMenu.getItems().addAll(item1, item2);
    task1.setContextMenu(contextMenu);
    mondayProgress.setProgress(0.5);
  }

  private void showPopupWindow() {
    View taskView = new TaskViewImpl(taskController);
    Stage popupStage = new Stage();
    popupStage.setScene(taskView.load());
    popupStage.showAndWait();
    taskController.run();
  }


  /**
   * Initializes a game of Whack-a-Mole.
   *
   * @throws IllegalStateException if the game board is not defined
   */
  @FXML
  public void run() throws IllegalStateException {
    taskQueue();
    handleCreateNewTask();
  }
}
