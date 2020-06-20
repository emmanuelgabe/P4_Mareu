package com.test.example.maru.Model;

public class Meeting {
    private int id;
    private Long startTime;
    private Long endTime;
    private String room;
    private String subject;
    private String emails;



    public Meeting(int id, Long startTime,Long endTime, String room, String subject, String emails) {
        this.id = id;
        this.startTime = startTime;
        this.room = room;
        this.endTime = endTime;
        this.subject = subject;
        this.emails = emails;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
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
