package com.example.hello;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Home extends VBox {
    GetDatabase getDatabase = new GetDatabase();
    ArrayList<Products> items =   getDatabase.allItems();
    ArrayList<Products> addedItems = new ArrayList<>();
    int[] addedCount = new int[100];
    TextField searchBar = new TextField();
    HBox searchArea = new HBox();
    BorderPane borderPane = new BorderPane();
    boolean isChecked = false;


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
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(10);
        flowPane.setVgap(5);
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("All");
        tabs.addAll(getDatabase.getTabs());

        for(String tab : tabs){
            Label button =  new Label(tab);
            flowPane.getChildren().add(button);
            button.getStyleClass().add("tabs");

            button.setOnMouseClicked(e->{
                container.setCenter(displayItems(getDatabase.allItems(tab)));
            });
        }


        container.setCenter(displayItems(items));
        container.setTop(flowPane);
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
                ProductCard productCard = new ProductCard(items.get(i),this);

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
                    borderPane.setRight(addedItems(addedItems,addedCount));
                });

                flowPane.getChildren().addAll(productCard);
            }
            currentItems.getStyleClass().add("scroll-pane");
            currentItems.setFitToWidth(true);
            //currentItems.setFitToHeight(true);
            currentItems.setContent(flowPane);
            return currentItems;
        }
        
        //for adding items to the checkout list

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
            int fi = i;
            card.total.setOnAction(e->{
                addedItems.remove(now);
                addedCount[fi] = 0;
                borderPane.setRight(addedItems(addedItems,addedCount));
            });
            //calculating the total  amount to pay
            amountToPay += now.price*addedCount[i];
            check.setText(amountToPay+" Ghs");

            gridPane.addRow(i,card);
            card.getStyleClass().add("side-bar-item");
        }

        check.setOnAction(e->{
            getDatabase.transactions(items,addedCount);
            addedItems.clear();
            Arrays.fill(addedCount, 0);
            isChecked = true;
            borderPane.setRight(addedItems(addedItems,addedCount));
        });

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
    Button total = new Button("");
    AddedItemsCard(Products products,int addedItemsCount){
        total.setText(""+ products.price*addedItemsCount);
        this.products = products;
        this.addedItemsCount = addedItemsCount;
        design();
    }

    void design(){
        Label name = new Label(products.name);
        name.setWrapText(true);
        Label price = new Label(""+products.price);
        price.setWrapText(true);
        Label times = new Label("X"+ addedItemsCount);
        times.setWrapText(true);


        total.getStyleClass().add("add-button");
        HBox hBox = new HBox(name,price,times);
        hBox.setSpacing(10);
        addRow(0,hBox,new Label(""),new Label(""), total);
        setHgap(20);
        GridPane.setHalignment(total, HPos.RIGHT);
    }
}
