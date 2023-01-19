package com.ahmad_usman_asad.i200420_i200551_i200761.Models;

public class ComplaintModel {

    String complaint_id;
    String user_id;
    String complaint;
    String complaint_type;
    String reply;
    Boolean replyGiven;
    Boolean replySeen;

    public ComplaintModel() {
        this.complaint_id = "";
        this.user_id = "";
        this.complaint = "";
        this.complaint_type = "";
        this.reply = "";
        this.replyGiven = false;
        this.replySeen = false;
    }

    public ComplaintModel(String complaint_id, String user_id, String complaint, String complaint_type, String reply, Boolean replyGiven, Boolean replySeen) {
        this.complaint_id = complaint_id;
        this.user_id = user_id;
        this.complaint = complaint;
        this.complaint_type = complaint_type;
        this.reply = reply;
        this.replyGiven = replyGiven;
        this.replySeen = replySeen;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getComplaint_type() {
        return complaint_type;
    }

    public void setComplaint_type(String complaint_type) {
        this.complaint_type = complaint_type;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Boolean getReplyGiven() {
        return replyGiven;
    }

    public void setReplyGiven(Boolean replyGiven) {
        this.replyGiven = replyGiven;
    }

    public Boolean getReplySeen() {
        return replySeen;
    }

    public void setReplySeen(Boolean replySeen) {
        this.replySeen = replySeen;
    }
}
