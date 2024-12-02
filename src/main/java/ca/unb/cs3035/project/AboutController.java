package ca.unb.cs3035.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutController {

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem help;

    @FXML
    private Button button;

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

        button.setOnAction(e -> {
            try {
                FXMLLoader toDoListFxmlLoader = new FXMLLoader(Main.class.getResource("to-do-list.fxml"));
                root.setTop(toDoListFxmlLoader.load());
                ((ToDoListController) toDoListFxmlLoader.getController()).setBorderPane(root);

                FXMLLoader taskListCentreFxmlLoader = new FXMLLoader(Main.class.getResource("task-list-centre.fxml"));

                root.setCenter(taskListCentreFxmlLoader.load());
                root.setBottom(Main.taskListTodayView);
                ((TaskListCentreController) taskListCentreFxmlLoader.getController()).setBorderPane(root);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }
}
