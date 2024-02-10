package com.example.food;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class OldOrdersController extends ParentController {
    @FXML
    private VBox listOfOlds;
    @FXML
    private void initialize(){
        Cashier cashier = Cashier.getInstance();
        for (Bill i: cashier.getList("1")) {
            Label label = new Label("Masa : "+ i.getTableID()+"  Ödenen Tutar : "+i.getPayment()+ " TL" +" Ödeme Türü : "
            + i.getPaymentType() + "Tarih : " + i.getDate() + " Ödeme durumu : " + i.getPaymentResult());
            label.setUserData(i);
            label.getStyleClass().add("label-stil");
            listOfOlds.getChildren().add(label);

        }
    }


    @FXML
    protected void goBack(ActionEvent event)throws IOException{
        prevPage();
    }

    @Override
    public void prevPage() {
        setContent("cashierPage");
    }

    @Override
    public void nextPage() {

    }
}
