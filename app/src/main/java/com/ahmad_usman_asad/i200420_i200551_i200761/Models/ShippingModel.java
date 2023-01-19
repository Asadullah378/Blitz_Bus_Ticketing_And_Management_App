package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import java.util.ArrayList;

public class ShippingModel {

    String shippingID;
    String address;
    String checkoutTime;
    String shippingType;
    String deliveryDate;
    ArrayList<ShippingItemModel> items;
    double price;
    String user_id;

    public ShippingModel() {
        this.shippingID = "";
        this.address = "";
        this.checkoutTime = "";
        this.shippingType = "";
        this.deliveryDate = "";
        this.items = new ArrayList<ShippingItemModel>();
        this.price = 0;
        this.user_id = "";
    }

    public ShippingModel(String shippingID, String address, String checkoutTime, String deliveryDate, String shippingType, ArrayList<ShippingItemModel> items, double price, String user_id) {
        this.shippingID = shippingID;
        this.address = address;
        this.checkoutTime = checkoutTime;
        this.deliveryDate = deliveryDate;
        this.shippingType = shippingType;
        this.items = items;
        this.price = price;
        this.user_id = user_id;
    }

    public String getShippingID() {
        return shippingID;
    }

    public void setShippingID(String shippingID) {
        this.shippingID = shippingID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public ArrayList<ShippingItemModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShippingItemModel> items) {
        this.items = items;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
