package com.test.example.mareu.Service;

import com.test.example.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1L, generateMeetingDate(-1, 9, 30), generateMeetingDate(-1, 11, 30), "Salle B", "Kick-off", "Alex@lamzone.com, Maxime@lamzone.com, Amandine@lamzone.com, Paul@lamzone.com"),
            new Meeting(2L, generateMeetingDate(0, 13, 0), generateMeetingDate(0, 14, 0), "Salle A", "Call USA", "Georges@lamzone.com, Henri@lamzone.com"),
            new Meeting(3L, generateMeetingDate(0, 15, 0), generateMeetingDate(0, 18, 30), "Salle B", "Webinar", " Laurent@lamzone.com, Pauline@lamzone.com, Robert@lamzone.com, Xavier@lamzone.com"),
            new Meeting(4L, generateMeetingDate(0, 10, 30), generateMeetingDate(0, 17, 45), "Amphithéâtre", "Assemblé générale", " Anne@lamzone.com, Bernard@lamzone.com, Christiane@lamzone.com, Claude@lamzone.com, Isabelle@lamzone.com, Madeleine@lamzone.com, Marcel@lamzone.com, Monique@lamzone.com, Nicole@lamzone.com,  Philippe@lamzone.com, Robert@lamzone.com, Yvonne@lamzone.com"),
            new Meeting(8L, generateMeetingDate(0, 10, 30), generateMeetingDate(0, 15, 30), "Salle F", "Maintenance technique", "François@lamzone.com, Laurent@lamzone.com, Nicolas@lamzone.com, Pauline@lamzone.com, Xavier@lamzone.com"),
            new Meeting(5L, generateMeetingDate(0, 10, 15), generateMeetingDate(0, 13, 0), "Salle C", "Entretiens", "Paul@lamzone.com, Robert@lamzone.com"),
            new Meeting(6L, generateMeetingDate(0, 13, 15), generateMeetingDate(0, 14, 15), "Salle D", "Entretiens", "François@lamzone.com, Laurent@lamzone.com, Nicolas@lamzone.com, Pauline@lamzone.com, Xavier@lamzone.com"),
            new Meeting(7L, generateMeetingDate(2, 14, 15), generateMeetingDate(2, 15, 15), "Salle D", "Entretiens", "François@lamzone.com, Laurent@lamzone.com, Nicolas@lamzone.com, Pauline@lamzone.com, Xavier@lamzone.com"),
            new Meeting(9L, generateMeetingDate(4, 9, 30), generateMeetingDate(4, 10, 30), "Salle A", "Entretiens", "François@lamzone.com, Laurent@lamzone.com, Nicolas@lamzone.com, Pauline@lamzone.com, Xavier@lamzone.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    public static Long generateMeetingDate(int day, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTimeInMillis();
    }
}

