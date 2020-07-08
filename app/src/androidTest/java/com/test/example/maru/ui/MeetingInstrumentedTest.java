package com.test.example.maru.ui;

import android.os.SystemClock;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.test.example.maru.DI.DI;
import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.service.MeetingApiService;
import com.test.example.maru.ui.MainActivity;
import com.test.example.maru.ui.meeting.add.AddMeetingActivity;
import com.test.example.maru.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.test.example.maru.Utils.DateTimeUtils.getStringTimeDateInformations;
import static com.test.example.maru.Utils.EmailStringUtils.formatEmails;
import static com.test.example.maru.Utils.EmailStringUtils.getEmailsSplit;
import static com.test.example.maru.service.DummyMeetingGenerator.generateMeetingDate;
import static com.test.example.maru.utils.CustomMatchers.atPosition;
import static com.test.example.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {


    @Rule
    public IntentsTestRule<MainActivity> intentsRule =
            new IntentsTestRule<>(MainActivity.class);

    private MainActivity mActivity;


    @Before
    public void setUp() {
        mActivity = intentsRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        onView(ViewMatchers.withId(R.id.activity_main_recyclerview_meetings))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingRecyclerView_deleteAction_shouldRemoveItem() {
        MeetingApiService mApiService = DI.getMeetingApiService();
        int itemCount = mApiService.getMeetings().size();
        onView(withId(R.id.activity_main_recyclerview_meetings)).check(withItemCount(itemCount));
        onView(withId(R.id.activity_main_recyclerview_meetings))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withText(R.string.dialog_delete_meeting_message))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()));
        onView(withText("OUI")).perform(click());
        onView(withId(R.id.activity_main_recyclerview_meetings)).check(withItemCount(itemCount - 1));
    }

    /**
     * Select meeting, detail fragment is display and check detail value
     */

    @Test
    public void meetingRecyclerView_shouldDisplayDetailFragmentWithCorrectInformation() {
        MeetingApiService mApiService = DI.getMeetingApiService();
        Meeting mMeeting = mApiService.getMeetings().get(3);
        onView(withId(R.id.activity_main_recyclerview_meetings)).perform(actionOnItemAtPosition(3, click()));
        // check information displayed
        onView(withId(R.id.fragment_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_detail_meeting_tv_subject)).check(matches(withText(mMeeting.getSubject())));
        onView(withId(R.id.fragment_detail_meeting_tv_room)).check(matches(withText(mMeeting.getRoom())));
        onView(withId(R.id.fragment_detail_meeting_tv_time)).check(matches(withText(getStringTimeDateInformations(mMeeting.getStartTime(), mMeeting.getEndTime(), mActivity))));
        if (!mActivity.getResources().getBoolean(R.bool.is_tablet)) {
            onView(withId(R.id.fragment_detail_meeting_tv_emails)).check(matches(withText(formatEmails(mMeeting.getEmails()))));
        } else {
            onView(withId(R.id.fragment_detail_meeting_tv_emails)).check(matches(withText(getEmailsSplit(1, mMeeting.getEmails()))));
            onView(withId(R.id.fragment_detail_meeting_tv_emails2)).check(matches(withText(getEmailsSplit(2, mMeeting.getEmails()))));
        }
    }

    /**
     * Start addMeetingActivity and check activity is display.
     * Add new meeting, check is display in RecyclerView and in detail fragment with right information.
     */
    @Test
    public void addNewMeetingFromMainActivity_NewMeetingIsShownInRecyclerViewAndDetailMeetingFragment() {
        MeetingApiService apiservice = DI.getMeetingApiService();
        Meeting newMeeting = new Meeting(12L, generateMeetingDate(0, 12, 00), generateMeetingDate(0, 13, 0), "Salle G", "reunion de test", "emailtest@test.fr");
        int itemCount = apiservice.getMeetings().size();
        // start addMeeting activity and check is display
        onView(withId(R.id.activity_main_fab_add)).perform(click());
        intended(hasComponent(AddMeetingActivity.class.getName()));
        // add new meeting
        onView(withId(R.id.activity_add_meeting_tie_subject)).perform(typeText(newMeeting.getSubject()));
        onView(withId(R.id.activity_add_meeting_actv_room)).perform(typeText(newMeeting.getRoom()));
        onView(withText(newMeeting.getRoom())).perform(click());
        onView(withId(R.id.activity_add_meeting_til_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_add_meeting_til_hour)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_add_meeting_til_duration)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_add_meeting_mactv_email)).perform(typeText(newMeeting.getEmails()));
        onView(withId(R.id.activity_add_meeting_btn_validate)).perform(click());
        // check new meeting is present in recyclerview
        onView(withId(R.id.activity_main_recyclerview_meetings)).check(withItemCount(itemCount + 1));
        // open detail meeting and check new meeting is present with right information
        onView(withId(R.id.activity_main_recyclerview_meetings)).perform(actionOnItemAtPosition((itemCount), click()));
        onView(withId(R.id.fragment_detail_meeting_tv_subject)).check(matches(withText(newMeeting.getSubject())));
        onView(withId(R.id.fragment_detail_meeting_tv_subject)).check(matches(withText(newMeeting.getSubject())));
        onView(withId(R.id.fragment_detail_meeting_tv_room)).check(matches(withText(newMeeting.getRoom())));
        onView(allOf(withId(R.id.fragment_detail_meeting_tv_time),isDisplayed())).
                check(matches(withText(getStringTimeDateInformations(newMeeting.getStartTime(), newMeeting.getEndTime(), mActivity))));
        onView(withId(R.id.fragment_detail_meeting_tv_emails)).check(matches(withText(newMeeting.getEmails() + "\n")));
    }

    /**
     * Use filter and check result display in recyclerview
     */

    @Test
    public void filter_RecyclerViewShouldBeConsistentWithFilter() {
        MeetingApiService apiservice = DI.getMeetingApiService();
        // Open filter
        onView(withId(R.id.filter_item)).perform(click());
        SystemClock.sleep(1100);
        // wright room in filter, check the result. Clear room filter and check the result
        onView(withId(R.id.content_filter_actv_room)).perform(typeText("Salle A"));
        onView(withText("Salle A")).perform(click());
        onView(withId(R.id.activity_main_recyclerview_meetings))
                .check(matches(atPosition(0, hasDescendant(withText(containsString("Salle A"))))));
        onView(allOf(withContentDescription("Clear text"), isDisplayed())).perform(click());
        onView(withId(R.id.activity_main_recyclerview_meetings)).check(withItemCount(apiservice.getMeetings().size()));
        // Choose startDate and endDate filter and check if the recyclerView display correct meeting
        onView(withId(R.id.content_filter_til_meeting_after_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.content_filte_til_meeting_before_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_main_recyclerview_meetings))
                .check(matches(atPosition(0, hasDescendant(withText(containsString(
                        new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(new Date())
                ))))));
    }
}

