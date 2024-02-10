package com.example.food;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.util.Objects;

public abstract class ParentController extends Conn{


    private static Pane contentPane;


    public static Label waiterLabel;


    public static Label tableLabel;

    @FXML
    public void setContent(String FXMLfileName) {

        try {

            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(FXMLfileName + ".fxml")));
            contentPane.getChildren().setAll(parent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setContentPane(Pane contentPane) {
        this.contentPane = contentPane;
    }

    public abstract void prevPage();

    public static void setCasier(Label casier) {
        ParentController.waiterLabel = casier;
    }

    public static void setTable(Label table) {
        ParentController.tableLabel = table;
    }

    public abstract void nextPage();
}
