package ca.unb.cs3035.project;

import javafx.scene.layout.BorderPane;

public class Controller {
    private BorderPane root;

    public void setBorderPane(BorderPane root) {
        this.root = root;
    }

    public BorderPane getBorderPane() {
        return this.root;
    }
}
