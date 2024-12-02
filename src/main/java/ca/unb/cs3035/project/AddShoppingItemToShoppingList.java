package ca.unb.cs3035.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddShoppingItemToShoppingList {
    static Boolean shoppingListItem = false;

    public static Boolean shoppingListItem(String shoppingName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("shopping-list-shopping.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Add Item to Wishlist");

        Scene secondaryScene = new Scene(root, 700, 300);

        Label shoppingNameLabel = (Label) secondaryScene.lookup("#itemNameLabel");

        Button shoppingSubmit = (Button) secondaryScene.lookup("#shoppingSubmit");
        Button shoppingCancel = (Button) secondaryScene.lookup("#shoppingCancel");

        shoppingNameLabel.setText(shoppingName);


        shoppingSubmit.setOnAction(e -> {
            shoppingListItem = true;
            secondaryStage.close();
        });

        shoppingCancel.setOnAction(e -> {
            shoppingListItem = false;
            secondaryStage.close();
        });

        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
        return shoppingListItem;
    }
}
