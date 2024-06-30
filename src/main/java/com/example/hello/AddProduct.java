package com.example.hello;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

class AddProduct extends StackPane{
    TextField name = new TextField("");
    TextField quantity = new TextField("");
    TextField  price = new TextField("");
    ComboBox<String> supplierComboBox = new ComboBox<>();
    TextField supplier = new TextField("");
    TextField category = new TextField("");
    TextField description = new TextField("");
    Button add = new Button("Add product");
    GetDatabase getDatabase = new GetDatabase();

    AddProduct(){
        ObservableList<String> suppliers = FXCollections.observableArrayList(getDatabase.getSupplier());
        supplierComboBox.setItems(suppliers);

        supplierComboBox.setOnAction(event -> {
            String selectedSupplier = supplierComboBox.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                supplier.setText(selectedSupplier);
            }
        });

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
        gridPane.addRow(3,new Label("suppliers"),supplierComboBox);
        gridPane.addRow(4,new Label("supplier"),supplier);
        gridPane.addRow(5,new Label("category"),category);
        gridPane.addRow(6,new Label("description"),description);
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
        description.setText("");
    }
}