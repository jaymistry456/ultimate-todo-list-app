package ca.unb.cs3035.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.IOException;

public class ShoppingListCentreController {
    @FXML
    private ToggleGroup shoppingGroup;

    @FXML
    private ToggleButton shoppingList;
    @FXML
    private ToggleButton wishList;

    @FXML
    private ToggleButton deletedList;

    @FXML
    private Label shoppingListType;

    private BorderPane root;

    public void setBorderPane(BorderPane root) {
        this.root = root;
    }

    @FXML
    public void initialize() throws IOException {
        shoppingListType.setText("Shopping List");
        shoppingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));

        try {
            shoppingGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {

                    if (new_toggle==null || new_toggle==shoppingList) {
                        shoppingListType.setText("Shopping List");
                        shoppingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.shoppingListView.displayList();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.shoppingListView);
                    }
                    else if (new_toggle==wishList) {
                        shoppingListType.setText("My Wishlist");
                        shoppingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.shoppingWishListView.displayList();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.shoppingWishListView);
                    }
                    else if(new_toggle==deletedList) {
                        shoppingListType.setText("Deleted Items");
                        shoppingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.shoppingDeletedListView.displayList();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.shoppingDeletedListView);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

