package com.test.example.maru;

import com.test.example.maru.DI.DI;
import com.test.example.maru.Model.Meeting;
import com.test.example.maru.service.DummyMeetingGenerator;
import com.test.example.maru.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Meeting service
 */

@RunWith(JUnit4.class)
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

        Meeting meetingToAdd = new Meeting( System.currentTimeMillis(),
                DummyMeetingGenerator.generateMeetingDate(-1, 9, 0),
                DummyMeetingGenerator.generateMeetingDate(-1, 10, 0),
                "Salle B",
                "réunion 4",
                "emails@outlook.fr");
        mService.createMeeting(meetingToAdd);
        assertTrue(mService.getMeetings().contains(meetingToAdd));
    }
}