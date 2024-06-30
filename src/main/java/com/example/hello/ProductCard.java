package com.example.hello;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class ProductCard extends VBox {
    Label name = new Label("Name");
    Label quantity = new Label("Quantity");
    Label price = new Label("Price");
    Label supplier = new Label("Supplier");
    Label category = new Label("Category");
    Label status = new Label("Status");
    Button add = new Button("");
    Products products;
    Home home;
    GetDatabase getDatabase = new GetDatabase();
    MenuItem delete = new MenuItem("delete");

    Label description = new Label("Description");
    boolean hasChanged = false;

    ProductCard(){

    }

    ProductCard(Products products, Home home) {
        this.description.setText(products.description);
        this.home = home;
        this.name.setText(products.name);
        this.price.setText(products.price < 100 ? "" + products.price : " " + products.price);
        this.quantity.setText("" + products.quantity);
        this.supplier.setText("supplier name : " + products.supplier);
        this.category.setText(products.category);
        this.products = products;

        if (products.quantity == 0)
            status.setText("Not Available");
        else if (products.quantity < 10)
            status.setText("about to finish");
        else status.setText("Available");

        design();
        edit();
    }

    void design() {
        VBox v = new VBox(supplier, description);
        getChildren().addAll(status, name, v,new Label(" "), buttonContainer());
        getStyleClass().add("product-card");
        setSpacing(15);
        description.setStyle("-fx-font-weight: 400; -fx-font-size: 14px; -fx-text-fill: grey");
        description.setLayoutY(-10);
        status.getStyleClass().add("status-style");
    }

    HBox buttonContainer() {
        add.setText(Integer.parseInt(quantity.getText().trim()) == 0 ? "Out of stock" : "buy@ " + price.getText());
        add.getStyleClass().add("add-button");
        VBox vContainer = new VBox(category, quantity);
        HBox buttonContainer = new HBox(vContainer, add);
        buttonContainer.setSpacing(100);
        buttonContainer.getStyleClass().add("bottom-style");
        quantity.setStyle("-fx-font-weight: 400; -fx-font-size: 14px; -fx-text-fill: grey");
        return buttonContainer;
    }

    void edit() {

        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("edit");

        contextMenu.getItems().addAll(edit, delete);

        edit.setOnAction(e -> {
            editComponent();
        });

        delete.setOnAction(e -> {
            getDatabase.deleteProduct(this.products.id);
            Platform.runLater(() -> home.displayItems(getDatabase.allItems()));
        });

        this.setOnContextMenuRequested(event -> {
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        });
    }

    void editComponent() {
        //for edit the product
        TextField name = new TextField(this.name.getText());
        TextField quantity = new TextField(this.quantity.getText().trim());
        TextField price = new TextField(this.price.getText());
        TextField supplier = new TextField(this.supplier.getText().trim());
        TextField dec = new TextField(this.description.getText());

        Button save = new Button("Save");
        Button cancel = new Button("cancel");

        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label("Name"), name);
        gridPane.addRow(1, new Label("Quantity"), quantity);
        gridPane.addRow(2, new Label("Price"), price);
        gridPane.addRow(3, new Label("Supplier"), supplier);
        gridPane.addRow(4,new Label("Description"),dec);
        gridPane.addColumn(1, save, cancel);


        // Create a new Stage and add the GridPane to it
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(gridPane));

        // Add an action to the cancel button to close the dialog
        cancel.setOnAction(event -> {
            dialogStage.close();
            System.out.println("is clicked");
        });

        save.setOnAction(e -> {
            String _name = name.getText();

            int _quantity = Integer.parseInt(quantity.getText().trim());

            int _price = Integer.parseInt(price.getText().trim());
            String _supplier = supplier.getText();
            String decs = dec.getText();
            getDatabase.editProduct(_name, _quantity, _price, _supplier, this.products.category, this.products.id,decs);

            hasChanged =
                    _name.equals(this.name.getText()) &&
                            _quantity == Integer.parseInt(this.quantity.getText().trim()) &&
                            _price == Integer.parseInt(this.price.getText().trim()) &&
                            _supplier.equals(this.supplier.getText());

            this.name.setText(_name);
            this.quantity.setText("quantity: " + _quantity);
            this.price.setText("" + _price);
            this.supplier.setText("supplier name : " + _supplier);
            this.description.setText(decs);

            dialogStage.close();
        });

        dialogStage.show();
    }

}