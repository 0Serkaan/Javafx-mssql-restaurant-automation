package com.example.food;

import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Waiter extends Conn implements Employee{
    private static int waiterID;
    private static String name;
    private static String lastName;
    private static double salary;

    private static Waiter instance;


    private Waiter(){
        waiterID = 0;
        name = null;
        lastName = null;
        salary = 0;

    }

    public static Waiter getInstance() {
        if (instance == null) {
            instance = new Waiter();
        }
        return instance;
    }

    public void setData(int waiterID,String name,String lastName,double salary){
        Waiter.waiterID = waiterID;
        Waiter.name = name;
        Waiter.lastName = lastName;
        Waiter.salary = salary;
    }

    @Override
    public boolean changePassword(String newPass) {
        return false;
    }

    @Override
    public void exit() {}

    public void checkTable(VBox selectedList){
        try  {

            String sqlSorgu = "SELECT masa_durum FROM MASA WHERE masa_id = ?";


            try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {
                Table table = Table.getInstance();
                int ID = table.getTableID() ;
                preparedStatement.setInt(1, ID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {

                        String value = resultSet.getString("masa_durum");
                        if (value.equals("0")){

                            updateTable("1",ID);
                            addBill(ID);
                        }
                        addOrder(findID(ID),selectedList);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTable(String newValue,int tableID){
        try  {String sqlSorgu = "UPDATE MASA SET masa_durum = ? WHERE masa_id = ?";
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {
                preparedStatement.setString(1, newValue);
                preparedStatement.setInt(2, tableID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(int ID, VBox selectedList){

        try  {String sqlSorgu = "INSERT INTO siparis (fatura_id, ad, adet, fiyat) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement =getConnection().prepareStatement(sqlSorgu)) {
                Orders order = new Orders(1,new Menu(1,"","",""));
                for (Orders data : order.getOrders(selectedList) ) {
                    preparedStatement.setInt(1, ID);
                    preparedStatement.setString(2, data.getMenu().getName());
                    preparedStatement.setInt(3, data.getCount());
                    preparedStatement.setDouble(4, data.getTotalPrice());


                    int etkilenenSatirSayisi = preparedStatement.executeUpdate();

                    if (etkilenenSatirSayisi > 0) {
                        System.out.println("Veri başarıyla eklendi. Eklenen satır sayısı:  " + etkilenenSatirSayisi);
                    } else {
                        System.out.println("Veri eklenirken bir hata oluştu.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int findID(int ID){
        try{String sqlSorgu = "SELECT fatura_id FROM FATURA WHERE masa_id = ? AND odeme_durum = ?";

            try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {

                preparedStatement.setInt(1, ID);
                preparedStatement.setInt(2, 0);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("fatura_id");
                    } else {
                        System.out.println("Belirtilen ID ve sayı değerine sahip kayıt bulunamadı.");
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
    public void addBill(int ID){
        try  {String sqlSorgu = "INSERT INTO FATURA (odenecek_tutar, masa_id, odeme_durum) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {


                preparedStatement.setDouble(1, 0);
                preparedStatement.setInt(2, ID);
                preparedStatement.setInt(3, 0);


                int etkilenenSatirSayisi = preparedStatement.executeUpdate();

                if (etkilenenSatirSayisi > 0) {
                    System.out.println("Fatura başarıyla eklendi. Eklenen satır sayısı: " + etkilenenSatirSayisi);
                } else {
                    System.out.println("Fatura eklenirken bir hata oluştu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public boolean login(String ID){
        if (auth(ID)){
        try  {
            String sqlSorgu = "SELECT * FROM GARSON WHERE garson_id = ?";

            try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSorgu)) {

                preparedStatement.setString(1, ID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int waiterID = resultSet.getInt("garson_id");
                        String name = resultSet.getString("garson_ad");
                        String lastname = resultSet.getString("garson_soyad");
                        double salary = resultSet.getDouble("garson_maas");



                        setData(waiterID,name,lastname,salary);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
        }else {return false;}

    }

    public int getGarsonID() {
        return waiterID;
    }

    public void setGarsonID(int garsonID) {
        Waiter.waiterID = garsonID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Waiter.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        Waiter.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        Waiter.salary = salary;
    }

}
