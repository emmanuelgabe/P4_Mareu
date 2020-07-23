package com.test.example.mareu.Service;

import com.test.example.mareu.Model.Meeting;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class
DummyMeetingApiService implements MeetingApiService {
    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<Meeting> meetingsFiltered = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override public List<Meeting> getMeetingsFiltered() {
        return meetingsFiltered;
    }

    @Override public void deleteMeetingsFiltered(Meeting meeting) {
        meetingsFiltered.remove(meeting);
    }

    @Override public void addMeetingsFiltered(Meeting meeting) {
        meetingsFiltered.add(meeting);
    }

    @Override public void resetMeetingsFiltered() {
        meetingsFiltered = DummyMeetingGenerator.generateMeetings();
    }
}
