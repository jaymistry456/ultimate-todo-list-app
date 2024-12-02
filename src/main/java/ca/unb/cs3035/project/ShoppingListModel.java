package ca.unb.cs3035.project;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ShoppingListModel {
    SimpleListProperty<ShoppingItemWidget> shoppingListProperty;
    SimpleListProperty<ShoppingItemWidget> shoppingWishListListProperty;
    SimpleListProperty<ShoppingItemWidget> shoppingDeletedListProperty;


    public ShoppingListModel() {
        ArrayList<ShoppingItemWidget> shoppingList = new ArrayList<ShoppingItemWidget>();
        ObservableList<ShoppingItemWidget> observableShoppingList = (ObservableList<ShoppingItemWidget>) FXCollections.observableArrayList(shoppingList);
        shoppingListProperty = new SimpleListProperty<ShoppingItemWidget>(observableShoppingList);

        ArrayList<ShoppingItemWidget> shoppingWishList = new ArrayList<ShoppingItemWidget>();
        ObservableList<ShoppingItemWidget> observableShoppingWishListList = (ObservableList<ShoppingItemWidget>) FXCollections.observableArrayList(shoppingWishList);
        shoppingWishListListProperty = new SimpleListProperty<ShoppingItemWidget>(observableShoppingWishListList);

        ArrayList<ShoppingItemWidget> shoppingDeletedList = new ArrayList<ShoppingItemWidget>();
        ObservableList<ShoppingItemWidget> observableShoppingDeletedList = (ObservableList<ShoppingItemWidget>) FXCollections.observableArrayList(shoppingDeletedList);
        shoppingDeletedListProperty = new SimpleListProperty<ShoppingItemWidget>(observableShoppingDeletedList);
    }

    public SimpleListProperty<ShoppingItemWidget> shoppingListProperty() {
        return this.shoppingListProperty;
    }
    public SimpleListProperty<ShoppingItemWidget> shoppingWishListListProperty() {
        return this.shoppingWishListListProperty;
    }
    public SimpleListProperty<ShoppingItemWidget> shoppingDeletedListProperty() {
        return this.shoppingDeletedListProperty;
    }

    public void addItemInShoppingList(String itemName, Boolean purchased) {
        shoppingListProperty.add(new ShoppingItemWidget(itemName, purchased));
    }

    public void addItemInShoppingWishList(String itemName, Boolean purchased) {
        shoppingWishListListProperty.add(new ShoppingItemWidget(itemName, purchased));
    }

    public void addItemInShoppingDeletedList(String itemName, Boolean purchased) {
        shoppingDeletedListProperty.add(new ShoppingItemWidget(itemName, purchased));
    }

}

