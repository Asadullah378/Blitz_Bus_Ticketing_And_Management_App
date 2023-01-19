package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

public class ScheduleModel {
    String schedule_id;
    String departure;
    String arrival;
    String departure_time;
    String arrival_time;
    double ticket_price;
    String bus_number;

    public ScheduleModel() {
        this.schedule_id = "";
        this.departure = "";
        this.arrival = "";
        this.departure_time = "";
        this.arrival_time = "";
        this.ticket_price = 0;
        this.bus_number = "";
    }

    public ScheduleModel(String schedule_id, String departure, String arrival, String departure_time, String arrival_time, double ticket_price, String bus_number) {
        this.schedule_id = schedule_id;
        this.departure = departure;
        this.arrival = arrival;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.ticket_price = ticket_price;
        this.bus_number = bus_number;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public double getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(double ticket_price) {
        this.ticket_price = ticket_price;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }
}
