package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

import java.util.ArrayList;

public class ScheduleTicketMergeModel {

    TicketModel ticket;
    ScheduleModel scheduleModel;
    String reservation_id;

    public ScheduleTicketMergeModel() {
        this.ticket = new TicketModel();
        this.scheduleModel = new ScheduleModel();
        this.reservation_id="";

    }

    public ScheduleTicketMergeModel(TicketModel ticket, ScheduleModel scheduleModel, String reservation_id) {
        this.ticket = ticket;
        this.scheduleModel = scheduleModel;
        this.reservation_id = reservation_id;
    }

    public TicketModel getTicket() {
        return ticket;
    }

    public void setTicket(TicketModel ticket) {
        this.ticket = ticket;
    }

    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }
}
