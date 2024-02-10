package com.example.food;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CashierController extends ParentController{

    @FXML
    private VBox billList;

    @FXML
    private VBox orderList;

    @FXML
    private Label priceLabel;

    @FXML
    private ChoiceBox<String> choiceBox;


    private double totalPriceOfOrders = 0;
    private String selected;

    private int IDOFBILL;

    public void initialize() {
       MenuList();
        choiceBox.getItems().addAll("Nakit","Kart");
        choiceBox.setValue("Ödeme Türü Seçiniz . ");
        choiceBox.setOnAction(event -> {
            selected = choiceBox.getValue();

        });
    }

    public void MenuList(){
        billList.getChildren().clear();
        Cashier cashier = Cashier.getInstance();
        for (Bill i: cashier.getList("0")) {
            try  {
                String sqlSorgu = "SELECT masa_durum FROM MASA WHERE masa_id = ?";
                try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {
                    preparedStatement.setInt(1, i.getTableID());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String value = resultSet.getString("masa_durum");
                            if (value.equals("0")){
            Label label = new Label("Masa : "+ i.getTableID());
            label.setUserData(i);
            label.setOnMouseClicked(this::labelAction);
            label.getStyleClass().add("label-stil");
            billList.getChildren().add(label);
           }}}}}catch (Exception e){e.getStackTrace();}
        }
    }

    protected void labelAction(MouseEvent event){
        orderList.getChildren().clear();
        Label label =(Label) event.getSource();

        Bill bill =(Bill) label.getUserData();
        Cashier cashier = Cashier.getInstance();
        double totalPrice = 0;
        for (Orders i : cashier.getOrderList(bill.getID())){
            IDOFBILL = bill.getID();
            Label listLabel = new Label();
            listLabel.setText("x" + i.getCount()+ i.getMenu().getName() + i.getTotalPrice());
            listLabel.getStyleClass().add("label-stil");
            orderList.getChildren().add(listLabel);
            totalPrice += i.getTotalPrice();
            totalPriceOfOrders += i.getTotalPrice();
        }
        priceLabel.setText("ÖDENECEK TUTAR : "+ totalPrice);
    }

    @FXML
    protected void takePayment(){
        Cashier cashier = Cashier.getInstance();
        cashier.takePayment(IDOFBILL,totalPriceOfOrders,choiceBox.getValue());
        MenuList();
    }

    @FXML
    protected void oldOrders() {
        nextPage();
    }

    @FXML
    protected void goBack(){
        prevPage();
    }

    @Override
    public void prevPage() {
        setContent("hello-view");
    }

    @Override
    public void nextPage() {
        setContent("oldOrders");
    }
}
