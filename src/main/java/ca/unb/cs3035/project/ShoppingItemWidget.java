package ca.unb.cs3035.project;

public class ShoppingItemWidget {
    private String itemName;
    private Boolean purchased=false;

    public ShoppingItemWidget(String itemName, Boolean purchased) {
        this.itemName = itemName;
        this.purchased = purchased;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }
}
