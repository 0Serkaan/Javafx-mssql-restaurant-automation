package com.example.food;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cashier extends Conn{

    private static int cashierID;
    private static String name;
    private static String lastName;
    private static double salary;

    private static Cashier instance;

    private Cashier(){
        cashierID = 0;
        name = null;
        lastName = null;
        salary = 0;

    }

    public static Cashier getInstance() {
        if (instance == null) {
            instance = new Cashier();
        }
        return instance;
    }

    public void setData(int waiterID,String name,String lastName,double salary){
        Cashier.cashierID = waiterID;
        Cashier.name = name;
        Cashier.lastName = lastName;
        Cashier.salary = salary;
    }

    public ArrayList<Bill> getList(String orderS) {
        ArrayList<Bill> billList = new ArrayList<>();
        try  {
            String query = "SELECT * FROM FATURA WHERE  odeme_durum = "+orderS;
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int ID = resultSet.getInt("fatura_id");
                        int tableID = resultSet.getInt("masa_id");
                        double payment = resultSet.getDouble("odenecek_tutar");
                        String paymentType = resultSet.getString("odeme_turu");
                        String date = resultSet.getString("islem_tarihi");

                        Bill bill= new Bill(ID,tableID,payment,paymentType,date);
                        billList.add(bill);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }

    public ArrayList<Orders> getOrderList(int ID) {
        ArrayList<Orders> orders = new ArrayList<>();
        try  {
            String query = "SELECT * FROM SIPARIS WHERE fatura_id = ?";
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                preparedStatement.setInt(1, ID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int count = resultSet.getInt("adet");
                        String name = resultSet.getString("ad");
                        double price = resultSet.getDouble("fiyat");
                        Orders order= new Orders(count,new Menu(price,name,"",""));
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void takePayment(int ID,double totalPrice,String paymentType){
        try {LocalDateTime now = LocalDateTime.now();

            String sqlSorgu = "UPDATE FATURA SET islem_tarihi = ?, odenecek_tutar = ? ,odeme_turu = ?, odeme_durum = ? WHERE fatura_id = ?";

            try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {
                preparedStatement.setObject(1, now);
                preparedStatement.setDouble(2, totalPrice);
                preparedStatement.setString(3,paymentType);
                preparedStatement.setInt(4,1);
                preparedStatement.setInt(5, ID);

                int etkilenenSatirSayisi = preparedStatement.executeUpdate();
                if (etkilenenSatirSayisi > 0) {
                    System.out.println("Satır başarıyla güncellendi. Güncellenen satır sayısı: " + etkilenenSatirSayisi);
                } else {
                    System.out.println("Belirtilen ID'ye sahip satır bulunamadı veya güncelleme başarısız oldu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
