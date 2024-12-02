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

public class ShoppingWishListView extends Pane {

    public ShoppingWishListView() {
        Main.shoppingListModel.shoppingWishListListProperty().addListener(new ListChangeListener<ShoppingItemWidget>() {
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

        for(ShoppingItemWidget s: Main.shoppingListModel.shoppingWishListListProperty) {
            HBox hBox = new HBox(30);

            Label itemName = new Label(s.getItemName());
            itemName.setPrefWidth(400);
            itemName.setFont(Font.font("Arial", 20));

            Button shoppingListButton = new Button("Add to Shopping List");
            shoppingListButton.setPrefWidth(150);
            shoppingListButton.setPrefHeight(30);
            shoppingListButton.setOnAction(e -> {
                try {
                    Boolean moveItemToShoppingList = AddShoppingItemToShoppingList.shoppingListItem(s.getItemName());
                    if(moveItemToShoppingList) {
                        Main.shoppingListModel.shoppingListProperty().add(s);
                        Main.shoppingListModel.shoppingWishListListProperty.remove(s);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                try {
                    ShoppingItemWidget editedItem = EditShopping.updateItemDetails(s.getItemName(), s.getPurchased());

                    if(editedItem!=null) {
                        itemName.setText(editedItem.getItemName());
                        s.setItemName(editedItem.getItemName());
                        s.setPurchased(editedItem.getPurchased());
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
                    Boolean deleteItem = DeleteShopping.deleteItem(s.getItemName(), true);
                    if(deleteItem) {
                        Main.shoppingListModel.shoppingWishListListProperty.remove(s);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(itemName, shoppingListButton, viewButton, editButton, deleteButton);

            shoppingListView.getItems().add(hBox);
        }

        HBox addButtonHBox = new HBox(5);

        Region regionOne = new Region();
        Button addItemButton = new Button("Add Item");
        addItemButton.setPrefSize(200, 40);
        addItemButton.setTextFill(Color.WHITE);
        addItemButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
        addItemButton.setAlignment(Pos.CENTER);
        addItemButton.setId("addItem");
        addItemButton.setOnAction(event -> {
            try {
                ShoppingItemWidget newItem = AddShopping.getItemDetails();
                if(newItem!=null) {
                    Main.shoppingListModel.shoppingWishListListProperty.add(newItem);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        Region regionTwo = new Region();

        addButtonHBox.getChildren().addAll(regionOne, addItemButton, regionTwo);

        HBox.setHgrow(regionOne, Priority.ALWAYS);
        HBox.setHgrow(regionTwo, Priority.ALWAYS);

        addItemButton.setLayoutX(600);
        addItemButton.setLayoutY(512);

        root.getChildren().addAll(shoppingListView, addItemButton);

        this.getChildren().add(root);
    }
}


