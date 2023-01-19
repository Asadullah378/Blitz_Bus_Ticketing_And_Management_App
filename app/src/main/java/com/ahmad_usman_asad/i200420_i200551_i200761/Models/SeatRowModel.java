package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import java.util.ArrayList;

public class SeatRowModel {

    ArrayList<SeatModel> seats;
    boolean select1;
    boolean select2;
    boolean select3;
    boolean select4;

    public SeatRowModel() {
        this.seats = new ArrayList<SeatModel>();
        select1=false;
        select2=false;
        select3=false;
        select4=false;
    }

    public SeatRowModel(ArrayList<SeatModel> seats) {
        this.seats = seats;
        select1=false;
        select2=false;
        select3=false;
        select4=false;
    }

    public ArrayList<SeatModel> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<SeatModel> seats) {
        this.seats = seats;
    }

    public boolean isSelect1() {
        return select1;
    }

    public void setSelect1(boolean select1) {
        this.select1 = select1;
    }

    public boolean isSelect2() {
        return select2;
    }

    public void setSelect2(boolean select2) {
        this.select2 = select2;
    }

    public boolean isSelect3() {
        return select3;
    }

    public void setSelect3(boolean select3) {
        this.select3 = select3;
    }

    public boolean isSelect4() {
        return select4;
    }

    public void setSelect4(boolean select4) {
        this.select4 = select4;
    }
}
