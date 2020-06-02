package com.test.example.maru.service;

import com.test.example.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1, "9H00", "Salle 2", "kick-off", "maxime@lamzone.com, alex@lamzone.com, amandine@lamzone.com, paul@lamzone.com"),
            new Meeting(2, "11H00", "Salle 7", "conf call USA", "robert@lamzone.com, laurent@lamzone.com"),
            new Meeting(3, "14H00", "Salle 5", "webinar", "robert@lamzone.com, laurent@lamzone.com, pauline@lamzone.com, xavier@lamzone.com")
    );
    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}
