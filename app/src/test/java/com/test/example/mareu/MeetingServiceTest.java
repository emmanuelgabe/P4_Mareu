package com.test.example.mareu;

import com.test.example.mareu.DI.DI;
import com.test.example.mareu.Model.Meeting;
import com.test.example.mareu.Service.DummyMeetingGenerator;
import com.test.example.mareu.Service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Meeting service
 */

@RunWith(MockitoJUnitRunner.class)
public class MeetingServiceTest {

    private MeetingApiService mService;

    @Before
    public void setup() {
        mService = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = mService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETING;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = mService.getMeetings().get(0);
        mService.deleteMeeting(meetingToDelete);
        assertFalse(mService.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meetingToAdd = new Meeting(System.currentTimeMillis(),
                DummyMeetingGenerator.generateMeetingDate(-1, 9, 0),
                DummyMeetingGenerator.generateMeetingDate(-1, 10, 0),
                "Salle B",
                "réunion 4",
                "emails@outlook.fr");
        mService.createMeeting(meetingToAdd);
        assertTrue(mService.getMeetings().contains(meetingToAdd));
    }

    /**
     * getMeetingsFiltered
     */
    @Test
    public void getMeetingsFiltered() {
        List<Meeting> meetingsFiltered = mService.getMeetingsFiltered();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETING;
        assertThat(meetingsFiltered, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    /**
     * deleteMeetingsFiltered
     */
    @Test
    public void deleteMeetingsFiltered() {
        Meeting meetingToDelete = mService.getMeetingsFiltered().get(0);
        mService.deleteMeetingsFiltered(meetingToDelete);
        assertFalse(mService.getMeetingsFiltered().contains(meetingToDelete));
    }

    /**
     * addMeetingsFiltered
     */
    @Test
    public void addMeetingsFiltered() {
        Meeting meetingToAdd = new Meeting(System.currentTimeMillis(),
                DummyMeetingGenerator.generateMeetingDate(-1, 9, 0),
                DummyMeetingGenerator.generateMeetingDate(-1, 10, 0),
                "Salle B",
                "réunion 4",
                "emails@outlook.fr");
        mService.addMeetingsFiltered(meetingToAdd);
        assertTrue(mService.getMeetingsFiltered().contains(meetingToAdd));
    }

    /**
     * check resetMeetingsFiltered after add and delete meeting
     */
    @Test
    public void resetMeetingsFiltered() {
        Meeting meetingToDelete = mService.getMeetingsFiltered().get(0);
        Meeting meetingToAdd = new Meeting(System.currentTimeMillis(),
                DummyMeetingGenerator.generateMeetingDate(-1, 0, 0),
                DummyMeetingGenerator.generateMeetingDate(1, 0, 0),
                "Salle B",
                "réunion 4",
                "emails@outlook.fr");
        mService.deleteMeetingsFiltered(meetingToDelete);
        mService.addMeetingsFiltered(meetingToAdd);
        mService.resetMeetingsFiltered();
        assertFalse(mService.getMeetingsFiltered().contains(meetingToAdd));
        assertTrue(mService.getMeetingsFiltered().contains(meetingToDelete));
    }
}