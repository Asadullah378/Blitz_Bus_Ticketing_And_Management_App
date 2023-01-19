package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserModel {

    String name;
    String email;
    String gender;
    double balance;
    ArrayList<TransactionModel> transactions;
    ArrayList<String> playerIDs;

    public UserModel() {
        this.name = "";
        this.email = "";
        this.gender = "";
        this.balance=0;
        transactions = new ArrayList<TransactionModel>();
        this.playerIDs = new ArrayList<String>();
    }

    public UserModel(String name, String email, String gender, double balance,ArrayList<TransactionModel> transactions, ArrayList<String> playerIDs) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.balance = balance;
        this.transactions = transactions;
        this.playerIDs = playerIDs;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<TransactionModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<String> getPlayerIDs() {
        return playerIDs;
    }

    public void setPlayerIDs(ArrayList<String> playerIDs) {
        this.playerIDs = playerIDs;
    }
}
