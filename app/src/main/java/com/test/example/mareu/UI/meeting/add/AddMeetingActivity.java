package com.test.example.mareu.UI.meeting.add;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.test.example.mareu.DI.DI;
import com.test.example.mareu.Model.Meeting;
import com.test.example.mareu.R;
import com.test.example.mareu.Utils.Picker.DatePickerFragment;
import com.test.example.mareu.Utils.Picker.TimePickerFragment;
import com.test.example.mareu.Service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.test.example.mareu.Utils.DateTimeUtils.getStringTimeDateInformation;
import static com.test.example.mareu.Utils.EmailStringUtils.sortEmailAlphabetically;

public class AddMeetingActivity extends AppCompatActivity implements TimePickerFragment.OnTimeSetListener, DatePickerFragment.OnDateSetListener, TextWatcher {
    public static final String DURATION_TIME_TAG = "newMeetingDuration";
    public static final String HOUR_MEETING_TAG = "newMeetingHour";
    public static final String DATE_MEETING_TAG = "newMeetingDate";

    @BindView(R.id.activity_add_meeting_btn_validate) Button mValidateBtn;
    @BindView(R.id.activity_add_meeting_til_duration) TextInputLayout mDurationTil;
    @BindView(R.id.activity_add_meeting_til_date) TextInputLayout mDateTil;
    @BindView(R.id.activity_add_meeting_til_hour) TextInputLayout mHourTil;
    @BindView(R.id.activity_add_meeting_til_email) TextInputLayout mEmailTil;
    @BindView(R.id.activity_add_meeting_til_subject) TextInputLayout mSubjectTil;
    @BindView(R.id.activity_add_meeting_til_room) TextInputLayout mRoomTil;
    @BindView(R.id.activity_add_meeting_actv_room) AutoCompleteTextView mRoomAutoCTV;
    @BindView(R.id.activity_add_meeting_mactv_email) MultiAutoCompleteTextView mEmailAutoMACTV;

    private Calendar mMeetingDate;
    private Calendar mMeetingDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    private void initView() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mMeetingDate = Calendar.getInstance();
        mMeetingDate.clear();
        mMeetingDuration = Calendar.getInstance();
        mMeetingDuration.clear();

        String[] room = getResources().getStringArray(R.array.room_array);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, room);
        mRoomAutoCTV.setAdapter(roomAdapter);
        String[] email = getResources().getStringArray(R.array.email);
        ArrayAdapter<String> emailAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, email);
        mEmailAutoMACTV.setAdapter(emailAdapter);
        mEmailAutoMACTV.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.activity_add_meeting_actionbar_title);

        mDateTil.getEditText().setOnClickListener(v -> {
            DatePickerFragment mDatePickerFragment = new DatePickerFragment(DATE_MEETING_TAG);
            mDatePickerFragment.show(getSupportFragmentManager(), DATE_MEETING_TAG);
        });
        mHourTil.getEditText().setOnClickListener(v -> {
            TimePickerFragment mTimePickerFragment = new TimePickerFragment(HOUR_MEETING_TAG);
            mTimePickerFragment.show(getSupportFragmentManager(), HOUR_MEETING_TAG);
        });

        mDurationTil.getEditText().setOnClickListener(v -> {
            TimePickerFragment mTimePickerFragment = new TimePickerFragment(DURATION_TIME_TAG);
            mTimePickerFragment.show(getSupportFragmentManager(), DURATION_TIME_TAG);
        });

        mSubjectTil.getEditText().addTextChangedListener(this);
        mRoomAutoCTV.addTextChangedListener(this);
        mEmailAutoMACTV.addTextChangedListener(this);
        mEmailAutoMACTV.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validate();
                return true;
            }
            return false;
        });
        mValidateBtn.setOnClickListener(v -> validate());
    }

    private void validate() {
        if (checkRequiredField()) {
            Calendar mMeetingEndDate = Calendar.getInstance();
            mMeetingEndDate.clear();
            mMeetingEndDate.setTime(mMeetingDate.getTime());
            mMeetingEndDate.add(Calendar.HOUR_OF_DAY, mMeetingDuration.get(Calendar.HOUR));
            mMeetingEndDate.add(Calendar.MINUTE, mMeetingDuration.get(Calendar.MINUTE));
            Meeting meeting = new Meeting(
                    System.currentTimeMillis(),
                    mMeetingDate.getTimeInMillis(),
                    mMeetingEndDate.getTimeInMillis(),
                    mRoomAutoCTV.getText().toString(),
                    mSubjectTil.getEditText().getText().toString(),
                    sortEmailAlphabetically(mEmailAutoMACTV.getText().toString()));
            if (meetingPlaceIsAvailable(meeting)) {
                MeetingApiService mApiService = DI.getMeetingApiService();
                mApiService.createMeeting(meeting);
                finish();
            }
        }
    }

    @Override
    public void onTimeSet(String tag, TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.hour_format), Locale.FRENCH);
        if (tag.equals(HOUR_MEETING_TAG)) {
            mMeetingDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mMeetingDate.set(Calendar.MINUTE, minute);
            mHourTil.getEditText().setText(df.format(mMeetingDate.getTime()));
            mHourTil.setError(null);
        } else if (tag.equals(DURATION_TIME_TAG)) {
            mMeetingDuration.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mMeetingDuration.set(Calendar.MINUTE, minute);
            mDurationTil.getEditText().setText(df.format(mMeetingDuration.getTime()));
            mDurationTil.setError(null);
        }
    }

    @Override
    public void onDateSet(String tag, DatePicker view, int year, int month, int dayOfMonth) {
        mMeetingDate.set(Calendar.YEAR, year);
        mMeetingDate.set(Calendar.MONTH, month);
        mMeetingDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.date_format), Locale.FRENCH);
        mDateTil.getEditText().setText(df.format(mMeetingDate.getTime()));
        mDateTil.setError(null);
    }

    private boolean checkRequiredField() {
        boolean requiredFiledIsNotEmpty = true;
        if (TextUtils.isEmpty(mRoomAutoCTV.getText())) {
            mRoomTil.setError(getString(R.string.activity_add_meeting_error_room_empty));
            requiredFiledIsNotEmpty = false;
        } else if (!Arrays.asList(getResources().getStringArray(R.array.room_array)).contains(mRoomAutoCTV.getText().toString())) {
            mRoomTil.setError(getString(R.string.activity_add_meeting_error_room_unknown));
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mSubjectTil.getEditText().getText())) {
            mSubjectTil.setError(getString(R.string.activity_add_meeting_error_subject_empty));
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mDurationTil.getEditText().getText())) {
            mDurationTil.setError(getString(R.string.activity_add_meeting_error_duration_empty));
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mHourTil.getEditText().getText())) {
            mHourTil.setError(getString(R.string.activity_add_meeting_error_hour_empty));
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mDateTil.getEditText().getText())) {
            mDateTil.setError(getString(R.string.activity_add_meeting_error_date_empty));
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mEmailAutoMACTV.getText())) {
            mEmailTil.setError(getString(R.string.activity_add_meeting_error_email_empty));
            requiredFiledIsNotEmpty = false;
        }
        return requiredFiledIsNotEmpty;
    }
    
    private boolean meetingPlaceIsAvailable(Meeting newMeeting) {
        MeetingApiService mApiService = DI.getMeetingApiService();
        List<Meeting> meetingList = mApiService.getMeetings();
        for (Meeting m : meetingList) {
            if (newMeeting.getRoom().equals(m.getRoom())
                    && (newMeeting.getStartTime() >= m.getStartTime() && newMeeting.getStartTime() < m.getEndTime()
                    || newMeeting.getEndTime() <= m.getEndTime() && newMeeting.getEndTime() > m.getStartTime())) {
                showDialogPlaceNotAvailable(m);
                return false;
            }
        }
        return true;
    }

    private void showDialogPlaceNotAvailable(Meeting m) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.dialog_add_meeting_error_title_timeslottaken);
        alertDialog.setMessage(getString(R.string.dialog_add_meeting_dialog_error_timeslottaken) + "\n" + m.getSubject() + " \n" + m.getRoom() + "\n " + getStringTimeDateInformation(m.getStartTime(), m.getEndTime(), this));
        alertDialog.setPositiveButton(getString(R.string.dialog_button_ok), (dialog, which) -> dialog.cancel());
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(mSubjectTil.getEditText().getText())) mSubjectTil.setError(null);
        if (!TextUtils.isEmpty(mRoomAutoCTV.getText())) mRoomTil.setError(null);
        if (!TextUtils.isEmpty(mEmailAutoMACTV.getText())) mEmailTil.setError(null);
    }
}