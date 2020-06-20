package com.test.example.maru.service;

import com.test.example.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public abstract class DummyMeetingGenerator {


    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1, generateMeetingDate(-1, -1, 30), generateMeetingDate(-1, 2, 0), "Salle B", "Kick-off", "Maxime@lamzone.com, Alex@lamzone.com, amandine@lamzone.com, paul@lamzone.com"),
            new Meeting(2, generateMeetingDate(0, -2, 0), generateMeetingDate(-1, 2, 0), "Salle A", "Call USA", "Henri@lamzone.com, Georges@lamzone.com"),
            new Meeting(3, generateMeetingDate(0, -1, 0), generateMeetingDate(0, 2, 30), "Salle B", "Webinar", "Robert@lamzone.com, laurent@lamzone.com, pauline@lamzone.com, xavier@lamzone.com"),
            new Meeting(1, generateMeetingDate(0, -4, 15), generateMeetingDate(0, 3, 45), "Amphithéâtre", "Assemblé générale", "Marcel@lamzone.com, Daniel@lamzone.com, Roger@lamzone.com, Christian@lamzone.com, Bernard@lamzone.com, Robert@lamzone.com, Paul@lamzone.com, Claude@lamzone.com, Henri@lamzone.com, Georges@lamzone.com, Nicolas@lamzone.com, François@lamzone.com, Patrick@lamzone.com, Nicole@lamzone.com, Madeleine@lamzone.com, Martine@lamzone.com, Sylvie@lamzone.com, Anne@lamzone.com, Jacqueline@lamzone.com, Isabelle@lamzone.com, Nathalie@lamzone.com, Catherine@lamzone.com, Monique@lamzone.com, Françoise@lamzone.com"),
            new Meeting(4, generateMeetingDate(0, 1, 0), generateMeetingDate(0, 2, 30), "Salle C", "Entretiens", "Robert@lamzone.com, Paul@lamzone.com"),
            new Meeting(5, generateMeetingDate(0, 2, 45), generateMeetingDate(0, 4, 0), "Salle D", "Entretiens", "Nicolas@lamzone.com, François@lamzone.com, laurent@lamzone.com, pauline@lamzone.com, xavier@lamzone.com"),
            new Meeting(6, generateMeetingDate(2, 0, 30), generateMeetingDate(2, 2, 45), "Salle D", "Entretiens", "Nicolas@lamzone.com, François@lamzone.com, laurent@lamzone.com, pauline@lamzone.com, xavier@lamzone.com"),
            new Meeting(6, generateMeetingDate(3, 1, 0), generateMeetingDate(3, 2, 0), "Salle A", "Entretiens", "Nicolas@lamzone.com, François@lamzone.com, laurent@lamzone.com, pauline@lamzone.com, xavier@lamzone.com"),
            new Meeting(6, generateMeetingDate(4, -1, 0), generateMeetingDate(4, 0, 0), "Salle A", "Entretiens", "Nicolas@lamzone.com, François@lamzone.com, laurent@lamzone.com, pauline@lamzone.com, xavier@lamzone.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    private static Long generateMeetingDate(int day, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.HOUR, hour);
        c.add(Calendar.MINUTE, minute);
        return c.getTimeInMillis();
    }
}

