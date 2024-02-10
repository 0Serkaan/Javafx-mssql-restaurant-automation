package com.example.food;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;



public class MainStage extends ParentController {

    @FXML
    public Pane contentPane;

    @FXML
    private Label waiterLabel;

    @FXML
    private Label tableLabel;

    @FXML
    public void initialize(){

    setContentPane(contentPane);
    setCasier(waiterLabel);
    setTable(tableLabel);
    setContent("hello-view");

    }


    @Override
    public void prevPage() {

    }

    @Override
    public void nextPage() {

    }
}