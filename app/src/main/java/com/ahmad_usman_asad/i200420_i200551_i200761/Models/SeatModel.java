package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SeatModel implements Parcelable {

    int seat_id;
    boolean booking_status;

    public SeatModel() {
        this.seat_id = 0;
        this.booking_status = false;
    }

    public SeatModel(int seat_id, boolean booking_status) {
        this.seat_id = seat_id;
        this.booking_status = booking_status;
    }

    protected SeatModel(Parcel in) {
        seat_id = in.readInt();
        booking_status = in.readByte() != 0;
    }

    public static final Creator<SeatModel> CREATOR = new Creator<SeatModel>() {
        @Override
        public SeatModel createFromParcel(Parcel in) {
            return new SeatModel(in);
        }

        @Override
        public SeatModel[] newArray(int size) {
            return new SeatModel[size];
        }
    };

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public boolean isBooking_status() {
        return booking_status;
    }

    public void setBooking_status(boolean booking_status) {
        this.booking_status = booking_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(seat_id);
        parcel.writeByte((byte) (booking_status ? 1 : 0));
    }
}
