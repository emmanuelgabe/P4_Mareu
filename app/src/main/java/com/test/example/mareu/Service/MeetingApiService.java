package com.test.example.mareu.Service;

import com.test.example.mareu.Model.Meeting;

import java.util.List;

/**
 * Meeting API client
 */
public interface MeetingApiService {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);

    List<Meeting> getMeetingsFiltered();

    void deleteMeetingsFiltered(Meeting meeting);

    void addMeetingsFiltered(Meeting meeting);

    void resetMeetingsFiltered();

}