package com.example.food;// GridController.java


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class tablesController extends ParentController{

    private static  int ROWS = 1;
    private static  int COLS = 1;
    private static double WIDTH;
    private static double HEIGHT;

    @FXML
    private GridPane gridPane;

    public void initialize() {
        int tableCount = tableCount();
        ROWS = (int) Math.floor(Math.sqrt(tableCount));
        COLS = (int) Math.ceil(Math.sqrt(tableCount));
        WIDTH = 1080/COLS;
        HEIGHT = 720/ROWS;
        createGrid();
    }

    private void createGrid() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Button button = createGridButton(row, col);

                gridPane.add(button, col, row);
            }
        }
    }

    private Button createGridButton(int rowIndex, int colIndex) {
        Button button = new Button();
        button.setOnAction(event -> {
            try {
                login(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        button.setMinSize(WIDTH, HEIGHT);
        button.setPrefSize(WIDTH, HEIGHT);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        int index = rowIndex * COLS + colIndex;
        button.setText(String.valueOf("Table : " + (int)(index+1)));
        File file = new File("src/main/resources/com/example/food/table.png");
        Image image = new Image(file.toURI().toString());

        ImageView iconView = new ImageView(image);
        iconView.setFitWidth(60);
        iconView.setFitHeight(60);
        button.setGraphic(iconView);
        button.setAlignment(Pos.CENTER);

        // Metni ve ikonu düğmenin içinde hizala
        button.setContentDisplay(ContentDisplay.TOP);

        // Metin ile ikon arasındaki boşluğu belirle
        button.setGraphicTextGap(5); // 5 piksel boşluk bırak
        button.setUserData(index+1);
        if (checkTable(index+1)){
            button.getStyleClass().add("tableButtonFull");
        }else button.getStyleClass().add("tableButtonEmpty");


        return button;
    }


    @FXML
    private void login(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Table table = Table.getInstance();
        table.setTableID((int)button.getUserData());
        tableLabel.setText("Table : "+table.getTableID());
        nextPage();
    }

    public boolean checkTable(int ID){
        try  {String sqlSorgu = "SELECT masa_durum FROM MASA WHERE masa_id = ?";
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {
                preparedStatement.setInt(1, ID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String value = resultSet.getString("masa_durum");
                        if (value.equals("0")){
                            return false;
                        }else return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return false;
    }

    @Override
    public void prevPage() {

    }

    @Override
    public void nextPage() {
        setContent("foodMenu");

    }
}
