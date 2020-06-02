package com.test.example.maru.Model;

public class Meeting {
    private int id;
    private String time;
    private String room;
    private String subject;
    private String emails;


    public Meeting(int id, String time, String room, String subject, String emails) {
        this.id = id;
        this.time = time;
        this.room = room;
        this.subject = subject;
        this.emails = emails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
