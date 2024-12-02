package ca.unb.cs3035.project;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskListUpcomingView extends Pane {

    public TaskListUpcomingView() {
        Main.taskListModel.upcomingTaskListProperty().addListener(new ListChangeListener<TaskWidget>() {
            @Override
            public void onChanged(Change<? extends TaskWidget> c) {
                try {
                    displayList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void displayList() throws IOException {
        this.getChildren().clear();

        Pane root = new Pane();
        root.setPrefSize(1400, 585);

        ListView<HBox> taskListView = new ListView<>();
        taskListView.setPrefSize(1350, 500);
        taskListView.setLayoutX(25);
        taskListView.setLayoutY(5);
        taskListView.setEditable(true);

        HBox titles = new HBox(15);

        Label taskNameTitle = new Label("Task Name");
        taskNameTitle.setPrefWidth(200);
        taskNameTitle.setFont(Font.font("Arial", 20));

        Label taskNameDescriptionTitle = new Label("Task Description");
        taskNameDescriptionTitle.setPrefWidth(300);
        taskNameDescriptionTitle.setFont(Font.font("Arial", 20));
        HBox.setHgrow(taskNameDescriptionTitle, Priority.ALWAYS);

        Label deadlineDateTitle = new Label("Deadline Date");
        deadlineDateTitle.setPrefWidth(150);
        deadlineDateTitle.setFont(Font.font("Arial", 20));

        Label deadlineTimeTitle = new Label("Deadline Time");
        deadlineTimeTitle.setPrefWidth(150);
        deadlineTimeTitle.setFont(Font.font("Arial", 20));

        Label taskStatusTitle = new Label("Task Status");
        taskStatusTitle.setPrefWidth(125);
        taskStatusTitle.setFont(Font.font("Arial", 20));

        Label taskImportant = new Label("Star?");
        taskImportant.setPrefWidth(50);
        taskImportant.setFont(Font.font("Arial", 20));

        titles.getChildren().addAll(taskNameTitle, taskNameDescriptionTitle, deadlineDateTitle, deadlineTimeTitle, taskStatusTitle, taskImportant);

        taskListView.getItems().add(titles);

        for(TaskWidget t: Main.taskListModel.upcomingTaskListProperty) {
            HBox hBox = new HBox(15);

            Label taskName = new Label(t.getTaskName());
            taskName.setPrefWidth(200);
            taskName.setFont(Font.font("Arial", 20));

            Label taskDescription = new Label(t.getTaskDescription());
            taskDescription.setPrefWidth(300);
            taskDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(taskDescription, Priority.ALWAYS);

            Label deadline = new Label(t.getDeadline());
            deadline.setPrefWidth(150);
            deadline.setFont(Font.font("Arial", 20));

            Label deadlinetime = new Label(t.getHours() + ":" + t.getMinutes() + " " + t.getAMPM());
            deadlinetime.setPrefWidth(150);
            deadlinetime.setFont(Font.font("Arial", 20));

            Label taskStatus = new Label(t.getStatus());
            taskStatus.setPrefWidth(125);
            taskStatus.setFont(Font.font("Arial", 20));


            CheckBox important = new CheckBox();
            important.setPrefWidth(50);
            important.setPadding(new Insets(5));

            if(t.getImportant()) {
                important.setSelected(true);
            }
            else {
                important.setSelected(false);
            }

            important.setOnAction(e -> {
                if(important.isSelected()) {
                    t.setImportant(true);
                    Main.taskListModel.addTaskInStarredListTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                }
                else {
                    t.setImportant(false);
                    for(TaskWidget task: Main.taskListModel.starredTaskListProperty) {
                        if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription()) {
                            Main.taskListModel.starredTaskListProperty.remove(task);
                            break;
                        }
                    }
                }
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);
            viewButton.setOnAction(e -> {
                try {
                    ViewTask.displayTaskDetails(t);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                try {
                    TaskWidget editedTask = EditTask.updateTaskDetails(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());

                    if(editedTask!=null) {
                        taskName.setText(editedTask.getTaskName());
                        taskDescription.setText(editedTask.getTaskDescription());
                        deadline.setText(editedTask.getDeadline());
                        deadlinetime.setText(editedTask.getHours() + ":" + editedTask.getMinutes() + " " + editedTask.getAMPM());
                        taskStatus.setText(editedTask.getStatus());

                        t.setTaskName(editedTask.getTaskName());
                        t.setTaskDescription(editedTask.getTaskDescription());
                        t.setDeadline(editedTask.getDeadline());
                        t.setHours(editedTask.getHours());
                        t.setMinutes(editedTask.getMinutes());
                        t.setAMPM(editedTask.getAMPM());
                        t.setStatus(editedTask.getStatus());

                        LocalDate date = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

                        if(t.getDeadline().substring(4,6).equals(formatter.format(date))) {
                            Main.taskListModel.upcomingTaskListProperty.remove(t);
                            Main.taskListModel.addTaskInTodaysTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(t.getTaskName(), false);
                    if(deleteTask) {
                        Main.taskListModel.deletedTaskListProperty.add(t);
                        Main.taskListModel.upcomingTaskListProperty.remove(t);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(taskName, taskDescription, deadline, deadlinetime, taskStatus, important, viewButton, editButton, deleteButton);

            taskListView.getItems().add(hBox);
        }

        HBox addButtonHBox = new HBox(5);

        Region regionOne = new Region();
        Button addTaskButton = new Button("Add Task");
        addTaskButton.setPrefSize(200, 40);
        addTaskButton.setTextFill(Color.WHITE);
        addTaskButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
        addTaskButton.setAlignment(Pos.CENTER);
        addTaskButton.setId("addTask");
        addTaskButton.setOnAction(event -> {
            try {
                TaskWidget newTask = AddTask.getTaskDetails();
                if(newTask!=null) {
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
                    if(newTask.getDeadline().substring(4,6).equals(formatter.format(date))) {
                        Main.taskListModel.addTaskInTodaysTaskList(newTask.getTaskName(), newTask.getTaskDescription(), newTask.getDeadline(), newTask.getHours(), newTask.getMinutes(), newTask.getAMPM(), newTask.getStatus());
                    }
                    else {
                        Main.taskListModel.addTaskInUpcomingTaskList(newTask.getTaskName(), newTask.getTaskDescription(), newTask.getDeadline(), newTask.getHours(), newTask.getMinutes(), newTask.getAMPM(), newTask.getStatus());
                    }

                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        Region regionTwo = new Region();

        addButtonHBox.getChildren().addAll(regionOne, addTaskButton, regionTwo);

        HBox.setHgrow(regionOne, Priority.ALWAYS);
        HBox.setHgrow(regionTwo, Priority.ALWAYS);

        addTaskButton.setLayoutX(600);
        addTaskButton.setLayoutY(512);

        root.getChildren().addAll(taskListView, addTaskButton);

        this.getChildren().add(root);
    }
}
