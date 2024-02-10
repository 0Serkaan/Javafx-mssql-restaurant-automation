package com.example.food;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splash();


    }

    private void splash() {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (double progress = 0.0; progress <= 1.0; progress += 0.01) {
                        final double currentProgress = progress;
                        Platform.runLater(() -> progressBar.setProgress(currentProgress));
                        Thread.sleep(30); // Adjust this delay based on your needs
                    }



                } catch (Exception e) {
                    System.out.println(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Stage stage = new Stage();
                            Scene scene = new Scene( FXMLLoader.load(getClass().getResource("mainPage.fxml")));
                            stage.setScene(scene);
                            stage.show();
                            root.getScene().getWindow().hide();
                        } catch (IOException ex) {
                            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }.start();
    }
}
