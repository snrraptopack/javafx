package com.example.hello;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class TotalValue extends GridPane {
    GetDatabase getDatabase = new GetDatabase();
    int len =getDatabase.getTotalSales().size();
    TotalValue(){
        design();
        getStyleClass().add("background-color");
        setAlignment(Pos.CENTER);
    }

    void design(){
        setVgap(10);
        for(int i=0;i<getDatabase.getTotalSales().size();i++){
            Total now = getDatabase.getTotalSales().get(i);
            addRow(i,new Total(now.totalValue, now.name,now.quantity,now.price));
        }
        Label overAll = new Label("over all total "+getDatabase.grandTotal);
        overAll.setStyle("-fx-background-color: green; -fx-font-size: 20px");
        addRow(len ,overAll);
    }

}

class Total extends HBox {
    int totalValue;
    String name;
    int quantity;
    int price;

    Total(int totalValue,String name,int quantity,int price){
        this.totalValue = totalValue;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        design();
        setStyle("-fx-background-color: white; -fx-font-size: 16px");
    }

    void design(){
        Label _name = new Label("product name : " +name);
        Label _quantity = new Label(" quantity : " +quantity);
        Label _price = new Label(" price : " +price);
        Label val = new Label("total : " +totalValue);
        getChildren().addAll(_name,_quantity,_price,val);
        setSpacing(10);
    }
}
