package com.example.hello;

public class Products {
   String name;
   int quantity;
   int price;
    String supplier;
   String category;
   int id;

    Products(String name,int quantity,int price,String supplier,String category,int id){
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.supplier = supplier;
        this.id = id;
    }


}
