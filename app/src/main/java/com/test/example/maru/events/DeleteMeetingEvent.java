package com.test.example.maru.events;

import com.test.example.maru.Model.Meeting;

public class DeleteMeetingEvent {
    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
