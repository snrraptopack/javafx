package com.example.hello;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TitleBar extends HBox {
    Button close = new Button("X");
    TitleBar(){
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #7827E6;");
        getChildren().addAll(button());
    }

    HBox button(Stage... stage){
        close.setStyle("-fx-font-size: 20px; -fx-padding: 10px 15px; -fx-background-color: #AA4FF6; -fx-font-weight: bold");
        HBox hBox = new HBox();
        close.setOnAction(e->{
            Platform.exit();
        });
        hBox.getChildren().add(close);
        return hBox;
    }
}
