package ca.unb.cs3035.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddWishList {
    static ShoppingItemWidget newItem;

    public static ShoppingItemWidget getItemDetails() throws IOException {
        newItem = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-wish-list.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Add Item");

        Scene secondaryScene = new Scene(root);

        TextField itemNameTextField = (TextField) secondaryScene.lookup("#itemNameInput");

        Button itemSubmit = (Button) secondaryScene.lookup("#itemSubmit");
        Button itemCancel = (Button) secondaryScene.lookup("#itemCancel");



        itemSubmit.setOnAction(e -> {
            newItem = new ShoppingItemWidget(itemNameTextField.getText(), false);
            secondaryStage.close();
        });

        itemCancel.setOnAction(e -> {
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
