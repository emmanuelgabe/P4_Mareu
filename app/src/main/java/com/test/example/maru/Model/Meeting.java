package com.test.example.maru.Model;

public class Meeting {
    private int id;
    private String startTime;
    private String room;
    private String subject;
    private String emails;
    private String endTime;


    public Meeting(int id, String startTime, String room, String subject, String emails) {
        this.id = id;
        this.startTime = startTime;
        this.room = room;
        this.endTime = endTime;
        this.subject = subject;
        this.emails = emails;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
