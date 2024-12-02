package ca.unb.cs3035.project;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class TaskListDeletedView extends Pane {
    public TaskListDeletedView() {
        Main.taskListModel.deletedTaskListProperty().addListener(new ListChangeListener<TaskWidget>() {
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
        taskNameTitle.setPrefWidth(150);
        taskNameTitle.setFont(Font.font("Arial", 20));

        Label taskNameDescriptionTitle = new Label("Task Description");
        taskNameDescriptionTitle.setPrefWidth(250);
        taskNameDescriptionTitle.setFont(Font.font("Arial", 20));
        HBox.setHgrow(taskNameDescriptionTitle, Priority.ALWAYS);

        Label deadlineTitle = new Label("Deadline Date");
        deadlineTitle.setPrefWidth(250);
        deadlineTitle.setFont(Font.font("Arial", 20));

        Label deadlineTimeTitle = new Label("Deadline Time");
        deadlineTimeTitle.setPrefWidth(150);
        deadlineTimeTitle.setFont(Font.font("Arial", 20));

        Label taskStatusTitle = new Label("Task Status");
        taskStatusTitle.setPrefWidth(125);
        taskStatusTitle.setFont(Font.font("Arial", 20));

        titles.getChildren().addAll(taskNameTitle, taskNameDescriptionTitle, deadlineTitle, deadlineTimeTitle, taskStatusTitle);

        taskListView.getItems().add(titles);

        for(TaskWidget t: Main.taskListModel.deletedTaskListProperty) {
            HBox hBox = new HBox(15);

            Label taskName = new Label(t.getTaskName());
            taskName.setPrefWidth(150);
            taskName.setFont(Font.font("Arial", 20));

            Label taskDescription = new Label(t.getTaskDescription());
            taskDescription.setPrefWidth(250);
            taskDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(taskDescription, Priority.ALWAYS);

            Label deadline = new Label(t.getDeadline());
            deadline.setPrefWidth(250);
            deadline.setFont(Font.font("Arial", 20));
            HBox.setHgrow(deadline, Priority.ALWAYS);

            Label deadlineTime = new Label(t.getHours() + ":" + t.getMinutes() + " " + t.getAMPM());
            deadlineTime.setPrefWidth(150);
            deadlineTime.setFont(Font.font("Arial", 20));

            Label taskStatus = new Label(t.getStatus());
            taskStatus.setPrefWidth(125);
            taskStatus.setFont(Font.font("Arial", 20));

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

            Button retoreButton = new Button("Retore");
            retoreButton.setPrefWidth(75);
            retoreButton.setPrefHeight(30);
            retoreButton.setOnAction(e -> {
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
                if(t.getDeadline().substring(4,6).equals(formatter.format(date))) {
                    Main.taskListModel.addTaskInTodaysTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                }
                else {
                    Main.taskListModel.addTaskInUpcomingTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                }
                Main.taskListModel.deletedTaskListProperty.remove(t);
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(t.getTaskName(), true);
                    if(deleteTask) {
                        Main.taskListModel.deletedTaskListProperty.remove(t);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(taskName, taskDescription, deadline, deadlineTime, taskStatus, viewButton, retoreButton, deleteButton);

            taskListView.getItems().add(hBox);
        }

        HBox emptyButtonHBox = new HBox(5);

        Region regionOne = new Region();
        Button emptyButton = new Button("Empty Bin");
        emptyButton.setPrefSize(200, 40);
        emptyButton.setTextFill(Color.WHITE);
        emptyButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
        emptyButton.setAlignment(Pos.CENTER);
        emptyButton.setId("emptyBin");
        emptyButton.setOnAction(event -> {
            try {
                Boolean emptyBin = EmptyBin.emptyBin();
                if(emptyBin) {
                    Main.taskListModel.deletedTaskListProperty.clear();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Region regionTwo = new Region();

        emptyButtonHBox.getChildren().addAll(regionOne, emptyButton, regionTwo);

        HBox.setHgrow(regionOne, Priority.ALWAYS);
        HBox.setHgrow(regionTwo, Priority.ALWAYS);

        emptyButton.setLayoutX(600);
        emptyButton.setLayoutY(512);

        root.getChildren().addAll(taskListView, emptyButton);

        this.getChildren().add(root);
    }
}
