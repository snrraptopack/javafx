package com.example.hello;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;


public class Home extends VBox {
    GetDatabase getDatabase = new GetDatabase();
    ArrayList<Products> items =   getDatabase.allItems();
    ArrayList<Products> addedItems = new ArrayList<>();
    int[] addedCount = new int[100];
    TextField searchBar = new TextField();
    HBox searchArea = new HBox();
    BorderPane borderPane = new BorderPane();


    Home() {
        getStyleClass().add("background-color");
        borderPane = productContainer();
        getChildren().addAll(borderPane); //searchArea
        searchSection();

    }
    void searchSection() {
        searchContainer();
        searchBarContainer();
    }
    void searchContainer() {
        searchArea.setAlignment(Pos.CENTER);
        searchArea.setPadding(new Insets(30));
        searchArea.setLayoutX(500);
        searchArea.setLayoutY(20);
        searchArea.getChildren().addAll(searchBar);
    }
    void searchBarContainer() {
        searchBar.setPrefWidth(350);
        searchBar.setPrefHeight(40);
        searchBar.setStyle("-fx-background-radius: 30px; -fx-border-width: 3px; -fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: " +
                "#E7E5DD; -fx-text-fill: black");
        searchBar.setPromptText("Search for products");
    }

    BorderPane productContainer(){
        BorderPane container = new BorderPane();
        container.setCenter(displayItems(items));
        container.setRight(addedItems(addedItems,addedCount));
        return container;
    }
    //this displays all the items in the database
    ScrollPane displayItems(ArrayList<Products> items){

            ScrollPane currentItems = new ScrollPane();
            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(10);
            flowPane.setVgap(10);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setPadding(new Insets(10,0,10,0));
            for(int i=0;i< items.size();i++){
                ProductCard productCard = new ProductCard(items.get(i));

                productCard.add.setOnAction(e->{
                    if(!isFound(productCard.products.id,addedItems)){
                        addedItems.add(productCard.products);
                        int current = addedItems.size()-1;
                        addedCount[current] +=1;
                    }else{
                        int current = addedItems.indexOf(productCard.products);
                        int indexOne=  items.indexOf(productCard.products);
                        int total = items.get(indexOne).quantity;
                        if(total > addedCount[current])
                            addedCount[current]+=1;
                    }
                    //borderPane.setRight(null);
                    borderPane.setRight(addedItems(addedItems,addedCount));
                    //getChildren().remove(0);
                    //getChildren().add(productContainer());
                });

                flowPane.getChildren().addAll(productCard);
            }
            currentItems.getStyleClass().add("scroll-pane");
            currentItems.setFitToWidth(true);
            currentItems.setContent(flowPane);
            return currentItems;
        }
        
        //for adding items to the checkout list
        void addedItems(){
             addedItems(addedItems, addedCount);
        }
        //checking if the product exist or not
    boolean isFound(int id,ArrayList<Products> items){
        for(Products item : items)
            if(item.id == id) return true;
        return false;
    }

     BorderPane addedItems(ArrayList<Products> items, int[] addedCount) {
        Button check = new Button("0 Ghs");
        check.getStyleClass().add("side-bar-button");
        GridPane gridPane = new GridPane();
        int amountToPay =0;
        for(int i = 0; i< items.size(); i++){
            Products now = items.get(i);
            AddedItemsCard card = new AddedItemsCard(now,addedCount[i]);
            //calculating the total  amount to pay
            amountToPay += now.price*addedCount[i];
            check.setText(amountToPay+" Ghs");

            gridPane.addRow(i,card);
            card.getStyleClass().add("side-bar-item");
        }

        BorderPane borderPane = new BorderPane();
        BorderPane.setAlignment(check,Pos.BOTTOM_CENTER);
        borderPane.setBottom(check);
        borderPane.setCenter(gridPane);
        borderPane.getStyleClass().add("side-bar");
        return borderPane;
    }

}

class AddedItemsCard extends GridPane{
    Products products;
    int addedItemsCount;
    AddedItemsCard(Products products,int addedItemsCount){
        this.products = products;
        this.addedItemsCount = addedItemsCount;
        design();
    }

    void design(){
        Label name = new Label(products.name);
        Label price = new Label(""+products.price);
        Label times = new Label("X"+ addedItemsCount);
        Button total = new Button(""+ products.price*addedItemsCount);
        total.getStyleClass().add("add-button");
        addRow(0,name,price,times,new Label (""),new Label(""),total);
        setHgap(20);
        GridPane.setHalignment(total, HPos.RIGHT);
    }
}
class ProductCard extends VBox{
    Label name = new Label("Name");
    Label quantity = new Label("Quantity");
    Label price = new Label("Price");
    Label supplier = new Label("Supplier");
    Label category = new Label("Category");
    Label status = new Label("Status");
    Button add = new Button("");
    Products products;
    ProductCard(Products products){
        this.name.setText(products.name);
        this.price.setText(products.price < 100 ? ""+products.price: " "+products.price);
        this.quantity.setText("quantity:"+products.quantity);
        this.supplier.setText("supplier name : "+products.supplier);
        this.category.setText(products.category);
        this.products = products;

        if(products.quantity == 0)
            status.setText("Not Available");
        else if(products.quantity < 10)
            status.setText("about to finish");
        else status.setText("Available");

        design();
    }

    void design(){
        getChildren().addAll(status,name,supplier,new Label(" "),buttonContainer());
        getStyleClass().add("product-card");
        setSpacing(15);
        status.getStyleClass().add("status-style");
    }

    HBox buttonContainer(){
        add.setText(Integer.parseInt(quantity.getText().split(":")[1].trim()) == 0 ? "Out of stock" : "buy@ " +price.getText());
        add.getStyleClass().add("add-button");
        VBox vContainer = new VBox(category,quantity);
        HBox buttonContainer = new HBox(vContainer,add);
        buttonContainer.setSpacing(100);
        buttonContainer.getStyleClass().add("bottom-style");
        quantity.setStyle("-fx-font-weight: 400; -fx-font-size: 14px; -fx-text-fill: grey");
        return buttonContainer;
    }


}