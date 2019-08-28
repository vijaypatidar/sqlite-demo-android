package com.example.sqlitedemo.model;

import android.database.Cursor;
import android.util.Log;

public class Product {
    private int id, quantity;
    private String name;
    private float price;

    public Product(int id, int quantity, String naame, float price) {
        this.id = id;
        this.quantity = quantity;
        this.name = naame;
        this.price = price;
    }

    public Product(Cursor cursor) {
        //used for creating object from cursor object || SQLite database
        this.id = cursor.getInt(0);
        this.name = cursor.getString(1);
        this.quantity = cursor.getInt(2);
        this.price = cursor.getFloat(3);
        Log.d("", "Product: ============== " + id + "  " + name + " " + price + " " + quantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
