package ca.unb.cs3035.project;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddShopping {
    static ShoppingItemWidget newItem;

    public static ShoppingItemWidget getItemDetails() throws IOException {
        newItem = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-shopping.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Add Item");

        Scene secondaryScene = new Scene(root, 700, 560);

        TextField itemNameTextField = (TextField) secondaryScene.lookup("#itemNameInput");

        Button shoppingSubmit = (Button) secondaryScene.lookup("#shoppingSubmit");
        Button shoppingCancel = (Button) secondaryScene.lookup("#shoppingCancel");



        shoppingSubmit.setOnAction(e -> {
            newItem = new ShoppingItemWidget(itemNameTextField.getText(), false);
            secondaryStage.close();
        });

        shoppingCancel.setOnAction(e -> {
            secondaryStage.close();
        });



        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();

        if(newItem!=null) {
            return newItem;
        }
        return null;
    }
}
