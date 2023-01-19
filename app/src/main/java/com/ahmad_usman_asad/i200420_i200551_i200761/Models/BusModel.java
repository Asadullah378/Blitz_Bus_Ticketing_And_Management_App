package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class BusModel implements Parcelable {

    String busNumber;
    int numSeats;
    String model;

    public BusModel() {

        this.busNumber = "";
        this.numSeats = 0;
        this.model = "";
    }

    public BusModel(String busNumber, int numSeats, String model) {
        this.busNumber = busNumber;
        this.numSeats = numSeats;
        this.model = model;
    }

    protected BusModel(Parcel in) {
        busNumber = in.readString();
        numSeats = in.readInt();
        model = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(busNumber);
        dest.writeInt(numSeats);
        dest.writeString(model);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusModel> CREATOR = new Creator<BusModel>() {
        @Override
        public BusModel createFromParcel(Parcel in) {
            return new BusModel(in);
        }

        @Override
        public BusModel[] newArray(int size) {
            return new BusModel[size];
        }
    };

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
