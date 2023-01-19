package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ShippingItemModel implements Parcelable {

    String name;
    double weight;

    public ShippingItemModel() {
        this.name = "";
        this.weight = 0;
    }

    public ShippingItemModel(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    protected ShippingItemModel(Parcel in) {
        name = in.readString();
        weight = in.readDouble();
    }

    public static final Creator<ShippingItemModel> CREATOR = new Creator<ShippingItemModel>() {
        @Override
        public ShippingItemModel createFromParcel(Parcel in) {
            return new ShippingItemModel(in);
        }

        @Override
        public ShippingItemModel[] newArray(int size) {
            return new ShippingItemModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(weight);
    }
}
