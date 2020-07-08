package com.test.example.maru.ui;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.test.example.maru.R;
import com.test.example.maru.ui.meeting.add.AddMeetingActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.test.example.maru.utils.CustomMatchers.hasTextInputLayoutErrorText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class AddInstrumentedTest {

    private static final int ITEMS_COUNT = 9;
    @Rule
    public ActivityTestRule<AddMeetingActivity> mActivityRule = new ActivityTestRule(AddMeetingActivity.class);
    private AddMeetingActivity mActivity;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * It's not possible to add meeting with empty field, error message should be shown.
     */
    @Test
    public void addMeeting_ValidateWithEmptyField_ShouldShowErrorMessage() {
        onView(ViewMatchers.withId(R.id.activity_add_meeting_btn_validate)).perform(click());
        onView(withId(R.id.activity_add_meeting_til_hour)).check(matches(hasTextInputLayoutErrorText(mActivity.getResources().getString(R.string.activity_add_meeting_error_hour_empty))));
        onView(withId(R.id.activity_add_meeting_til_subject)).check(matches(hasTextInputLayoutErrorText(mActivity.getResources().getString(R.string.activity_add_meeting_error_subject_empty))));
        onView(withId(R.id.activity_add_meeting_til_date)).check(matches(hasTextInputLayoutErrorText(mActivity.getResources().getString(R.string.activity_add_meeting_error_date_empty))));
        onView(withId(R.id.activity_add_meeting_til_duration)).check(matches(hasTextInputLayoutErrorText(mActivity.getResources().getString(R.string.activity_add_meeting_error_duration_empty))));
        onView(withId(R.id.activity_add_meeting_til_email)).check(matches(hasTextInputLayoutErrorText(mActivity.getResources().getString(R.string.activity_add_meeting_error_email_empty))));
        onView(withId(R.id.activity_add_meeting_til_room)).check(matches(hasTextInputLayoutErrorText(mActivity.getResources().getString(R.string.activity_add_meeting_error_room_empty))));
    }

    /**
     * it's not possible to add meeting with unknown room, specific error message should be shown
     */
    @Test
    public void addMeeting_validateWithUnknownRoom_ShouldShowErrorMessage() {
        onView(withId(R.id.activity_add_meeting_actv_room)).perform(typeText("unknown room"));
        onView(withId(R.id.activity_add_meeting_btn_validate)).perform(closeSoftKeyboard(), click());
        onView(withId(R.id.activity_add_meeting_til_room)).check(matches(hasTextInputLayoutErrorText(mActivity.getResources().getString(R.string.activity_add_meeting_error_room_unknown))));
    }

    /**
     * it's not possible to add meeting if an other meeting is is present at this time in the room
     */
    @Test
    public void addMeeting_ValidateWhileAnOtherMeetingIsPresent_ShouldShowDialogWithMeetingInformation() {
        onView(withId(R.id.activity_add_meeting_tie_subject)).perform(typeText("Rexnion de test"));
        onView(withId(R.id.activity_add_meeting_actv_room)).perform(typeText("Salle F"));
        onView(withText("Salle F")).perform(click());
        onView(withId(R.id.activity_add_meeting_til_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_add_meeting_til_hour)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_add_meeting_til_duration)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_add_meeting_mactv_email)).perform(typeText("emailtest@test.fr"));
        onView(withId(R.id.activity_add_meeting_btn_validate)).perform(click());
        onView(withText(R.string.dialog_add_meeting_error_title_timeslottaken))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()));
    }
}