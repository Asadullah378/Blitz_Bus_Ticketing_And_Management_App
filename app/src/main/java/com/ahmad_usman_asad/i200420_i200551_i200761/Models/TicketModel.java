package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

public class TicketModel {

    int seat_id;
    double price;
    String qr;

    public TicketModel() {
        this.seat_id = 0;
        this.price = 0;
        this.qr="";
    }

    public TicketModel(int seat_id, double price,String qr) {
        this.seat_id = seat_id;
        this.price = price;
        this.qr = qr;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
}
