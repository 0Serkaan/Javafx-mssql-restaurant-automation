package com.example.food;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Orders extends Conn{
    private int count;
    private Menu menu;
    private double totalPrice;




    public Orders(int count, Menu menu) {
        this.count = count;
        this.menu = menu;
        totalPrice = count * menu.getPrice();
    }

    public static double getOrdersTotalPrice(ArrayList<Orders> orders){
        double total = 0;
        for (Orders i : orders){
            total+= i.getTotalPrice();
        }
        return total;
    }

    public  ArrayList<Orders> getOrders(VBox selectedList){
        ArrayList<Orders> listOfOrders = new ArrayList<>();
        for (javafx.scene.Node node : selectedList.getChildren()) {

            HBox hb = (HBox) node;
            Label countLabel = (Label) hb.getChildren().get(1);
            int countOfMenu =Integer.parseInt(countLabel.getText());

            Label nameOfFoodLabel = (Label) hb.getChildren().get(2);
            String nameOfFood = nameOfFoodLabel.getText();
            System.out.println(nameOfFood);
            for (Menu i: genereteMenuList()) {
                if (i.getName().equals(nameOfFood)){
                    System.out.println("eklendi");
                    listOfOrders.add(new Orders(countOfMenu,i));
                }
            }
        }
        return listOfOrders;
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
