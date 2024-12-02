package ca.unb.cs3035.project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ToDoListController {

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem help;

    @FXML
    private MenuItem about;

    @FXML
    private Label taskListTitle;

    @FXML
    private Label meetingListTitle;

    @FXML
    private Label shoppingListTitle;

    private BorderPane root;

    public void setBorderPane(BorderPane root) {
        this.root = root;
    }

    public BorderPane getBorderPane() {
        return this.root;
    }

    @FXML
    public void initialize() throws IOException {

        close.setOnAction(event -> {
            Stage primaryStage = (Stage) root.getScene().getWindow();
            primaryStage.close();
        });

        help.setOnAction(event -> {
            FXMLLoader helpFxmlLoader = new FXMLLoader(Main.class.getResource("help.fxml"));
            try {
                root.setTop(helpFxmlLoader.load());
                ((HelpController) helpFxmlLoader.getController()).setBorderPane(root);
                root.setCenter(null);
                root.setBottom(null
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        about.setOnAction(event -> {
            FXMLLoader aboutFxmlLoader = new FXMLLoader(Main.class.getResource("about.fxml"));
            try {
                root.setTop(aboutFxmlLoader.load());
                ((AboutController) aboutFxmlLoader.getController()).setBorderPane(root);
                root.setCenter(null);
                root.setBottom(null
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        taskListTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            taskListTitle.setStyle("-fx-font-size: 30; -fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: lightblue;");
            meetingListTitle.setStyle("-fx-font-size: 30;");
            shoppingListTitle.setStyle("-fx-font-size: 30;");
            FXMLLoader taskListFxmlLoader = new FXMLLoader(Main.class.getResource("task-list-centre.fxml"));
            try {
                root.setCenter(taskListFxmlLoader.load());
                ((TaskListCentreController) taskListFxmlLoader.getController()).setBorderPane(root);
                root.setBottom(Main.taskListTodayView);
                taskListTitle.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        meetingListTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            taskListTitle.setStyle("-fx-font-size: 30;");
            meetingListTitle.setStyle("-fx-font-size: 30; -fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: lightblue;");
            shoppingListTitle.setStyle("-fx-font-size: 30;");
            FXMLLoader meetingListFxmlLoader = new FXMLLoader(Main.class.getResource("meeting-list-centre.fxml"));
            try {
                root.setCenter(meetingListFxmlLoader.load());
                ((MeetingListCentreController) meetingListFxmlLoader.getController()).setBorderPane(root);
                root.setBottom(Main.meetingListTodayView);
                taskListTitle.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }));

        shoppingListTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            taskListTitle.setStyle("-fx-font-size: 30;");
            meetingListTitle.setStyle("-fx-font-size: 30;");
            shoppingListTitle.setStyle("-fx-font-size: 30; -fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: lightblue;");

            FXMLLoader shoppingListFxmlLoader = new FXMLLoader(Main.class.getResource("shopping-list-centre.fxml"));
            try {
                root.setCenter(shoppingListFxmlLoader.load());
                ((ShoppingListCentreController) shoppingListFxmlLoader.getController()).setBorderPane(root);
                root.setBottom(Main.shoppingListView);
                taskListTitle.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }));

    }

}
