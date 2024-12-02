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

public class ShoppingDeletedListView extends Pane {

    public ShoppingDeletedListView() {
        Main.shoppingListModel.shoppingDeletedListProperty().addListener(new ListChangeListener<ShoppingItemWidget>() {
            @Override
            public void onChanged(Change<? extends ShoppingItemWidget> c) {
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

        ListView<HBox> shoppingListView = new ListView<>();
        shoppingListView.setPrefSize(1350, 500);
        shoppingListView.setLayoutX(25);
        shoppingListView.setLayoutY(5);
        shoppingListView.setEditable(true);

        HBox titles = new HBox(15);

        Label itemNameTitle = new Label("Item Name");
        itemNameTitle.setPrefWidth(400);
        itemNameTitle.setFont(Font.font("Arial", 20));

        titles.getChildren().addAll(itemNameTitle);

        shoppingListView.getItems().add(titles);

        for(ShoppingItemWidget s: Main.shoppingListModel.shoppingDeletedListProperty) {
            HBox hBox = new HBox(30);

            Label itemName = new Label(s.getItemName());
            itemName.setPrefWidth(400);
            itemName.setFont(Font.font("Arial", 20));


            Button restoreButton = new Button("Restore");
            restoreButton.setPrefWidth(75);
            restoreButton.setPrefHeight(30);
            restoreButton.setOnAction(e -> {
                    Main.shoppingListModel.shoppingDeletedListProperty.remove(s);
                    Main.shoppingListModel.shoppingListProperty.add(s);
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);
            viewButton.setOnAction(e -> {
                try {
                    ViewShopping.displayItemDetails(s);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteItem = DeleteShopping.deleteItem(s.getItemName(), true);
                    if(deleteItem) {
                        Main.shoppingListModel.shoppingDeletedListProperty.remove(s);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(itemName, restoreButton, viewButton, deleteButton);

            shoppingListView.getItems().add(hBox);
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
                    Main.shoppingListModel.shoppingDeletedListProperty.clear();
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


        root.getChildren().addAll(shoppingListView, emptyButton);

        this.getChildren().add(root);
    }
}


