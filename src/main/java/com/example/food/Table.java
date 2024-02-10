package com.example.food;

public class Table {
    private static Table instance;
    private int tableID;

    private Table(){
        // Boş bir private constructor
        // Yalnızca bu sınıf içinde örnek oluşturulabilir
        tableID = 0;
    }

    public static Table getInstance() {
        if (instance == null) {
            instance = new Table();
        }
        return instance;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int newID) {
        tableID = newID;
    }
}
