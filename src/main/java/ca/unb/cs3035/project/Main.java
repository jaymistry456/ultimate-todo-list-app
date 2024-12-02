package ca.unb.cs3035.project;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    public static final Controller controller = new Controller();

    public static final TaskListModel taskListModel = new TaskListModel();
    public static final MeetingListModel meetingListModel = new MeetingListModel();
    public static final ShoppingListModel shoppingListModel = new ShoppingListModel();

    public static final TaskListTodayView taskListTodayView = new TaskListTodayView();
    public static final TaskListUpcomingView taskListUpcomingView = new TaskListUpcomingView();
    public static final TaskListStarredView taskListStarredView = new TaskListStarredView();
    public static final TaskListDeletedView taskListDeletedView = new TaskListDeletedView();
    public static final TaskListCentreController taskListCentreController = new TaskListCentreController();

    public static final MeetingListTodayView meetingListTodayView = new MeetingListTodayView();
    public static final MeetingListUpcomingView meetingListUpcomingView = new MeetingListUpcomingView();
    public static final MeetingListStarredView meetingListStarredView = new MeetingListStarredView();
    public static final MeetingListDeletedView meetingListDeletedView = new MeetingListDeletedView();
    public static final MeetingListCentreController meetingListCentreController = new MeetingListCentreController();

    public static final ShoppingListView shoppingListView = new ShoppingListView();
    public static final ShoppingWishListView shoppingWishListView = new ShoppingWishListView();
    public static final ShoppingDeletedListView shoppingDeletedListView = new ShoppingDeletedListView();

    public static final ShoppingListCentreController shoppingListCentreController = new ShoppingListCentreController();



    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        BorderPane root = fxmlLoader.load();
        ((Controller) fxmlLoader.getController()).setBorderPane(root);

        try {
            FXMLLoader splashScreenFxmlLoader = new FXMLLoader(Main.class.getResource("splash-screen.fxml"));
            StackPane pane = splashScreenFxmlLoader.load();
            root.setTop(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    FXMLLoader toDoListFxmlLoader = new FXMLLoader(Main.class.getResource("to-do-list.fxml"));
                    root.setTop(toDoListFxmlLoader.load());
                    ((ToDoListController) toDoListFxmlLoader.getController()).setBorderPane(root);

                    FXMLLoader taskListCentreFxmlLoader = new FXMLLoader(Main.class.getResource("task-list-centre.fxml"));

                    root.setCenter(taskListCentreFxmlLoader.load());
                    root.setBottom(taskListTodayView);
                    ((TaskListCentreController) taskListCentreFxmlLoader.getController()).setBorderPane(root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");


        taskListModel.addTaskInTodaysTaskList("Task name", "Description", formatter.format(date), "11", "55", "PM", "Completed");
        taskListModel.addTaskInTodaysTaskList("Task name 2", "Description 2", formatter.format(date), "11", "55", "PM",  "In Progress");

        meetingListModel.addMeetingInTodaysMeetingList("Meeting1", "Description", formatter.format(date), "11", "00", "PM", "11", "55", "PM", "55", "Completed");
        meetingListModel.addMeetingInTodaysMeetingList("Meeting2", "Description2", formatter.format(date), "11", "00", "PM", "11", "55", "PM", "55", "Completed");


        shoppingListModel.addItemInShoppingList("Bananas", false);
        shoppingListModel.addItemInShoppingList("Apples", false);

        Scene scene = new Scene(root, 1400, 800);


        primaryStage.setTitle("The Ultimate To Do List!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}