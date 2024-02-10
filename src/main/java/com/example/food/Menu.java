package com.example.food;

import java.util.ArrayList;

public class Menu {
    private double price;
    private String name;
    private String type;

    private String cal;




    public Menu(double price, String name, String type,String cal) {
        this.price = price;
        this.name = name;
        this.type = type;
        this.cal = cal;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
