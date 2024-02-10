package com.example.food;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller extends ParentController{


public void initialize(){
    waiterLabel.setText("Waiter : ");
    tableLabel.setText("Table : ");
}

    @FXML
    private PasswordField pass;

    @FXML
    protected void numKeys(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = (String) clickedButton.getUserData();
        pass.setText(pass.getText()+buttonText);
    }

    @FXML
    protected void del() {
        if(!pass.getText().isEmpty()){
            pass.setText(pass.getText().substring(0,pass.getText().length()-1));
        }
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        Waiter waiter = Waiter.getInstance();
        if (waiter.login(pass.getText())){
            waiterLabel.setText("Waiter : "+waiter.getName()+" "+waiter.getLastName());
            nextPage();
        }
        else {
            Stage errorStage = new Stage();
            errorStage.initModality(Modality.APPLICATION_MODAL);
            errorStage.initStyle(StageStyle.UNDECORATED);

            // Küçük pencerenin içeriği
            StackPane layout = new StackPane();
            Label label = new Label("Incorrect password");
            label.getStyleClass().add("errorLabel");
            Button closeButton = new Button("Close");
            VBox vBox = new VBox();
            vBox.getStyleClass().add("errorBox");
            closeButton.getStyleClass().add("errorButton");
            closeButton.setOnAction(e -> errorStage.close()); // Kapat butonu işlevselliği
            vBox.getChildren().addAll(label,closeButton);
            layout.getChildren().add(vBox);

            Scene scene = new Scene(layout, 400, 100);
            errorStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            errorStage.showAndWait();
        }
    }

    @FXML
    protected void goCashier(ActionEvent event) throws IOException {
        prevPage();
        }

    @Override
    public void prevPage() {
        setContent("cashierPage");
    }

    @Override
    public void nextPage() {
        setContent("tables");
    }
}