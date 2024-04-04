package com.example.hello;
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
    int currentSize = database.allItems().size();
    MainPage(){
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Style.css")).toExternalForm());
        setTop(sideBar);
        handleAddProduct();
        setCenter(home);
        setBottom(new Label());
        items(home);


        home.displayItems(database.allItems());
    }

    void items(Home home){
        Label[] tabs = {sideBar.home,sideBar.addProduct,sideBar.billing,sideBar.Profile};
        Node[] pane = {home,addProduct,new Label("remove product"),new Button("four")};
        for (int i=0;i<tabs.length;i++){
            int finalI = i;
            tabs[i].setOnMouseClicked(e->{
                if(finalI == 0 && currentSize != database.allItems().size()){
                    setCenter(new Home());
                }else{
                    this.setCenter(pane[finalI]);
                }


            });
        }
    }

    void handleAddProduct(){
        addProduct.add.setOnAction(e->{
            if(addProduct.isValid()){
                String _name = addProduct.name.getText();
                int _quantity = Integer.parseInt(addProduct.quantity.getText());
                int _price = Integer.parseInt(addProduct.price.getText());
                String _supplier = addProduct.supplier.getText();
                String _category = addProduct.category.getText();
                int id = addProduct.generatedId();
                database.addProduct(_name,_quantity,_price,_supplier,_category,id);
                addProduct.emptyArea();
                System.out.println("before: " + database.allItems().size());
                home.displayItems(database.allItems());
            }
        });
    }

}