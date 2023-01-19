package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

public class TransactionModel {

    double amount;
    String transactionTime;
    String type;
    String name;

    public TransactionModel() {
        this.amount = 0;
        this.transactionTime = "";
        this.type = "";
        this.name = "";
    }

    public TransactionModel(double amount, String transactionTime, String type, String name) {
        this.amount = amount;
        this.transactionTime = transactionTime;
        this.type = type;
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
