package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import java.util.ArrayList;

public class ReservationModel {

    String reservation_id;
    String user_id;
    String schedule_id;
    String reservation_date;
    ArrayList<TicketModel> tickets;

    public ReservationModel() {
        this.reservation_id = "";
        this.user_id = "";
        this.schedule_id = "";
        this.reservation_date = "";
        this.tickets = new ArrayList<TicketModel>();
    }

    public ReservationModel(String reservation_id, String user_id, String schedule_id, String reservation_date, ArrayList<TicketModel> tickets) {
        this.reservation_id = reservation_id;
        this.user_id = user_id;
        this.schedule_id = schedule_id;
        this.reservation_date = reservation_date;
        this.tickets = tickets;
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public ArrayList<TicketModel> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<TicketModel> tickets) {
        this.tickets = tickets;
    }
}
