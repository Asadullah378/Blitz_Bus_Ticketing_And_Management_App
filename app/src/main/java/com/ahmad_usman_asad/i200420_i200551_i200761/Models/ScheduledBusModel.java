package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import java.util.ArrayList;

public class ScheduledBusModel {

    String schedule_id;
    String bus_number;
    ArrayList<SeatModel> seats;
    int remaining_seats;
    int total_seats;

    public ScheduledBusModel() {
        this.schedule_id = "";
        this.bus_number = "";
        this.seats = new ArrayList<SeatModel>();
        this.remaining_seats = 0;
        this.total_seats = 0;
    }

    public ScheduledBusModel(String schedule_id, String bus_number, ArrayList<SeatModel> seats, int remaining_seats, int total_seats) {
        this.schedule_id = schedule_id;
        this.bus_number = bus_number;
        this.seats = seats;
        this.remaining_seats = remaining_seats;
        this.total_seats = total_seats;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public ArrayList<SeatModel> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<SeatModel> seats) {
        this.seats = seats;
    }

    public int getRemaining_seats() {
        return remaining_seats;
    }

    public void setRemaining_seats(int remaining_seats) {
        this.remaining_seats = remaining_seats;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }
}
