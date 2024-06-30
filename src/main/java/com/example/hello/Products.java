package com.example.hello;

public class Products {
   String name;
   int quantity;
   int price;
    String supplier;
   String category;
   int id;
   String description;

    Products(String name,int quantity,int price,String supplier,String category,int id,String description){
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.supplier = supplier;
        this.id = id;
        this.description = description;
    }


}
