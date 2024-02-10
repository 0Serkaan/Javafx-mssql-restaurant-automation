package com.example.food;

public class Bill {
    private int ID;
    private int tableID;

    private double payment;

    private String paymentType;

    private String paymentResult;

    private String date;
    public Bill(int ID, int tableID,double payment,String paymentType, String date) {
        this.ID = ID;
        this.tableID = tableID;
        this.payment = payment;
        this.paymentType = paymentType;
        this.date = date;
        paymentResult = "ÖDENDİ";
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentResult() {
        return paymentResult;
    }

    public void setPaymentResult(String paymentResult) {
        this.paymentResult = paymentResult;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }
}
