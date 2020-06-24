package com.test.example.maru.service;

import com.test.example.maru.Model.Meeting;

import java.util.List;
/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all Meeting
     * @return {@link Meeting}
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a Meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a Meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);
}