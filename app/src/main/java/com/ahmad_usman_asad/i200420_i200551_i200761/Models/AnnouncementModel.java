package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

public class AnnouncementModel {

    String id;
    String title;
    String body;
    String date;

    public AnnouncementModel() {
        this.id="";
        this.title = "";
        this.body = "";
        this.date = "";
    }

    public AnnouncementModel(String id, String title, String body, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
