package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

public class ReservationScheduleMergeModel {

    ScheduleModel scheduleModel;
    ReservationModel reservationModel;

    public ReservationScheduleMergeModel() {
    }

    public ReservationScheduleMergeModel(ScheduleModel scheduleModel, ReservationModel reservationModel) {
        this.scheduleModel = scheduleModel;
        this.reservationModel = reservationModel;
    }

    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    public ReservationModel getReservationModel() {
        return reservationModel;
    }

    public void setReservationModel(ReservationModel reservationModel) {
        this.reservationModel = reservationModel;
    }
}
