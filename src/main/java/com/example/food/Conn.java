package com.example.food;

import java.sql.*;
import java.util.ArrayList;

public class Conn {
    private final Connection connection;

    public Conn() {
        String url = "jdbc:sqlserver://LAPTOP-L4C7J67K;Database=rest_otomasyon;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "root";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean auth(String ID){
        try {String sorgu = "SELECT COUNT(*) AS countResult FROM GARSON WHERE garson_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sorgu);

            preparedStatement.setString(1, ID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int resultCount = resultSet.getInt("countResult");
                System.out.println(resultCount);

                return resultCount > 0;
            }
            return false;
        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }


    public ArrayList<Menu> genereteMenuList(){
        ArrayList<Menu> menuList = new ArrayList<>();
        try  {
            String query = "SELECT urun_fiyat, urun_kalori, urun_ad, urun_tur FROM URUN";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        double price = resultSet.getDouble("urun_fiyat");
                        String name = resultSet.getString("urun_ad");
                        String type = resultSet.getString("urun_tur");
                        String cal = resultSet.getString("urun_kalori");

                        Menu menu = new Menu(price, name, type, cal);
                        menuList.add(menu);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public int tableCount(){
        int rowCount = 0;
        try {
            String query = "SELECT COUNT(*) AS row_count FROM MASA";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        rowCount = resultSet.getInt("row_count");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(rowCount);
        return rowCount;
    }

}
