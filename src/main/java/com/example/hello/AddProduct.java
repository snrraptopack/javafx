package com.example.hello;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

class AddProduct extends StackPane{
    TextField name = new TextField("");
    TextField quantity = new TextField("");
    TextField  price = new TextField("");
    TextField supplier = new TextField("");
    TextField category = new TextField("");
    Button add = new Button("Add product");

    AddProduct(){
        getChildren().add(component());
        getStyleClass().add("background-color");
    }
    int generatedId(){
        String generated = "";
        for(int i=0;i<5;i++){
            generated += (int)(Math.random()*10);
        }
        return  Integer.parseInt(generated);
    }
    GridPane component(){
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0,new Label("product name : "),name);
        gridPane.addRow(1,new Label("quantity"),quantity);
        gridPane.addRow(2,new Label("price"),price);
        gridPane.addRow(3,new Label("supplier"),supplier);
        gridPane.addRow(4,new Label("category"),category);
        gridPane.addColumn(1,add);
        styles();
        return  gridPane;
    }

    void styles(){
        add.getStyleClass().add("login-button");
       TextField[] textFields = {name,quantity,price,supplier,category};
       for(TextField textField : textFields){
           textField.getStyleClass().add("login-input-field");
       }
    }

    boolean isValid(){
        TextField[] textFields = {name,quantity,price,supplier,category};
        for(TextField textField : textFields){
            if(textField.getText().isEmpty())
                return false;
        }
        return true;
    }

    void emptyArea(){
        name.setText("");
        quantity.setText("");
        price.setText("");
        supplier.setText("");
        category.setText("");
    }
}