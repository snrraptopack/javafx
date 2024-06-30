package com.example.hello;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.util.Objects;
public class MainPage extends BorderPane {
    GetDatabase database = new GetDatabase();
    NavigationBar sideBar = new NavigationBar();
    Home home = new Home();
    AddProduct addProduct = new AddProduct();
    ProductCard productCard = new ProductCard();
    int currentSize = database.allItems().size();
    ManageProduct manageProduct = new ManageProduct(GetDatabase.getCategorySales());
    TotalValue totalValue = new TotalValue();

    MainPage() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Style.css")).toExternalForm());
        setTop(sideBar);
        handleAddProduct();
        setCenter(home);
        setBottom(new Label());
        items();

    }

    void items() {
        Label[] tabs = {sideBar.home, sideBar.addProduct, sideBar.billing, sideBar.Profile};
        Node[] pane = {home, addProduct,manageProduct, totalValue};
        for (int i = 0; i < tabs.length; i++) {
            int finalI = i;
            tabs[i].setOnMouseClicked(e -> {
                setCenter(pane[finalI]);
                handleDeleteProduct(pane[finalI]);
                if (pane[finalI] instanceof Home) {
                    // Update the home UI
                   setCenter(new Home());
                }
                if(pane[finalI] instanceof ManageProduct){
                    setCenter(new ManageProduct(GetDatabase.getCategorySales()));
                }

//                if(pane[finalI] instanceof AddProduct) {
//                    setCenter(new AddProduct());
//                }
            });
        }
    }

    void handleAddProduct() {
        addProduct.add.setOnAction(e -> {
            if (addProduct.isValid()) {
                String _name = addProduct.name.getText();
                int _quantity = Integer.parseInt(addProduct.quantity.getText());
                int _price = Integer.parseInt(addProduct.price.getText());
                String _supplier = addProduct.supplier.getText();
                String _category = addProduct.category.getText();
                String _description =  addProduct.description.getText();
                int id = addProduct.generatedId();
                database.addProduct(_name, _quantity, _price, _supplier, _category, id,_description);
                addProduct.emptyArea();

                Platform.runLater(() -> home.displayItems(database.allItems()));
            }
        });
    }

    void handleDeleteProduct(Node home) {
        productCard.delete.setOnAction(e -> {
            try {
                database.deleteProduct(productCard.products.id);

                if(home instanceof Home){
                    setCenter(new Home());
                }

                System.out.println("current size : " + currentSize);
                System.out.println("database size: " + database.allItems().size());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
