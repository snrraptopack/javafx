package com.example.hello;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NavigationBar extends VBox {
    Label myLogo = new Label("Order Me");
    Label home = new Label("Home");
    Label addProduct = new Label("Add Product");
    Label billing = new Label("Remove Product");
    Label Profile = new Label("Profile");
    NavigationBar() {
        getStyleClass().add("navigationBar");
        setSpacing(2);
        this.setAlignment(Pos.CENTER);
        getChildren().addAll(new Label(), navigationButtons());
        setPrefHeight(200);
        buttonsStyle();

    }
    HBox navigationButtons(){

        HBox navigationButtons = new HBox(home,addProduct,billing,Profile);
        navigationButtons.maxWidthProperty().bind(this.widthProperty().divide(2));
        navigationButtons.setPrefHeight(60);
        navigationButtons.setAlignment(Pos.CENTER);
        navigationButtons.setLayoutY(50);
        navigationButtons.setStyle("-fx-background-color: white; -fx-background-radius: 30px");

        for(Node child : navigationButtons.getChildren()){
            HBox.setHgrow(child, javafx.scene.layout.Priority.ALWAYS);
        }
       return  navigationButtons;
    }
    void buttonsStyle(){
        Label[] buttons = {addProduct,home,Profile,billing};
        for (Label button : buttons) {
            button.getStyleClass().add("navigation-buttons");
            button.setLayoutY(70);
        }
    }
}
