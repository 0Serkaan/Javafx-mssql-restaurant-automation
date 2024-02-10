module com.example.food {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;


    opens com.example.food to javafx.fxml;
    exports com.example.food;
}