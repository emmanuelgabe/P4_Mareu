package com.test.example.maru.ui.meeting.add;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.test.example.maru.DI.DI;
import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.Utils.Picker.DatePickerFragment;
import com.test.example.maru.Utils.Picker.TimePickerFragment;
import com.test.example.maru.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingActivity extends AppCompatActivity implements TimePickerFragment.OnTimeSetListener, DatePickerFragment.OnDateSetListener, TextWatcher {
    public static final String DURATION_TIME_TAG = "DurationPicker";
    public static final String HOUR_MEETING_TAG = "HourPicker";
    public static final String DATE_MEETING_TAG = "DateMeeting";

    @BindView(R.id.activity_add_meeting_btn_validate) Button mValidateBtn;
    @BindView(R.id.activity_add_meeting_til_duration) TextInputLayout mDurationTil;
    @BindView(R.id.activity_add_meeting_til_date) TextInputLayout mDateTil;
    @BindView(R.id.activity_add_meeting_til_hour) TextInputLayout mHourTil;
    @BindView(R.id.activity_add_meeting_til_email) TextInputLayout mEmailTil;
    @BindView(R.id.activity_add_meeting_til_subject) TextInputLayout mSubjectTil;
    @BindView(R.id.activity_add_meeting_til_room) TextInputLayout mRoomTil;
    @BindView(R.id.activity_add_meeting_actv_room) AutoCompleteTextView mRoomAutoCTV;
    @BindView(R.id.activity_add_meeting_mactv_email) MultiAutoCompleteTextView mEmailAutoMACTV;

    private Calendar meetingDate;
    private Calendar meetingStartHour;
    private Calendar meetingDuration;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        initview();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    private void initview() {
        meetingDate = Calendar.getInstance();
        meetingDuration = Calendar.getInstance();

        String[] room = getResources().getStringArray(R.array.room_array);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, room);
        mRoomAutoCTV.setAdapter(roomAdapter);
        String[] email = getResources().getStringArray(R.array.email);
        ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, email);
        mEmailAutoMACTV.setAdapter(emailAdapter);
        mEmailAutoMACTV.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ajouter une réunion");

        if (isTablet) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

        mValidateBtn.setOnClickListener(v -> {
            if (checkRequiredField() && meetingPlaceAvaiable()) {
                // meetingDuration.add(Calendar.DATE,meetingDate.getTime());
                Calendar meetingEndDate = Calendar.getInstance();
                meetingEndDate.setTime(meetingDate.getTime());
                meetingEndDate.add(Calendar.HOUR, meetingDuration.get(Calendar.HOUR));
                meetingEndDate.add(Calendar.MINUTE, meetingDuration.get(Calendar.MINUTE));
                Meeting mMeeting = new Meeting(
                        System.currentTimeMillis(),
                        meetingDate.getTimeInMillis(),
                        meetingEndDate.getTimeInMillis(),
                        mRoomAutoCTV.getText().toString(),
                        mSubjectTil.getEditText().getText().toString(),
                        sortEmailAlphabetically(mEmailAutoMACTV.getText().toString()));
                MeetingApiService mApiService = DI.getMeetingApiService();
                mApiService.createMeeting(mMeeting);
                finish();
                Toast.makeText(this, "réunion ajouté avec succès", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onTimeSet(String tag, TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.FRENCH);
        if (tag == HOUR_MEETING_TAG) {
            meetingDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            meetingDate.set(Calendar.MINUTE, minute);
            mHourTil.getEditText().setText(df.format(meetingDate.getTime()));
            mHourTil.setError(null);
        } else if (tag == DURATION_TIME_TAG) {
            meetingDuration.set(Calendar.HOUR_OF_DAY, hourOfDay);
            meetingDuration.set(Calendar.MINUTE, minute);
            mDurationTil.getEditText().setText(df.format(meetingDuration.getTime()));
            mDurationTil.setError(null);
        }
    }

    @Override
    public void onDateSet(String tag, DatePicker view, int year, int month, int dayOfMonth) {
        meetingDate.set(Calendar.YEAR, year);
        meetingDate.set(Calendar.MONTH, month);
        meetingDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        mDateTil.getEditText().setText(df.format(meetingDate.getTime()));
        mDateTil.setError(null);
    }

    private boolean checkRequiredField() {
        boolean requiredFiledIsNotEmpty = true;
        if (TextUtils.isEmpty(mRoomAutoCTV.getText())) {
            mRoomTil.setError("Veuillez saisir le lieu de la réunion");
            requiredFiledIsNotEmpty = false;
        } else if (!Arrays.asList(getResources().getStringArray(R.array.room_array)).contains(mRoomAutoCTV.getText().toString())) {
            mRoomTil.setError("Le lieu de réunion saisit n'est pas répertorié");
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mSubjectTil.getEditText().getText())) {
            mSubjectTil.setError("veuillez saisir le sujet de la réunion");
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mDurationTil.getEditText().getText())) {
            mDurationTil.setError("Veuillez saisir la durée de la réunion");
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mHourTil.getEditText().getText())) {
            mHourTil.setError("Veuillez saisir l'heure de la réunion");
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mDateTil.getEditText().getText())) {
            mDateTil.setError("Veuillez saisir la date de la réunion");
            requiredFiledIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(mEmailAutoMACTV.getText())) {
            mEmailTil.setError("Veuillez saisir le lieu de la réunion");
            requiredFiledIsNotEmpty = false;
        }
        return requiredFiledIsNotEmpty;
    }

    private boolean meetingPlaceAvaiable() {
        //TODO check avaiable and sow alertdialog
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(mSubjectTil.getEditText().getText())) mSubjectTil.setError(null);
        if (!TextUtils.isEmpty(mRoomAutoCTV.getText())) mRoomTil.setError(null);
        if (!TextUtils.isEmpty(mEmailAutoMACTV.getText())) mEmailTil.setError(null);
    }

    private String sortEmailAlphabetically(String emails) {
        String[] emailArray = emails
                .substring(0, emails.length() - 2)
                .split(",");
        Arrays.sort(emailArray);
        return Arrays.toString(emailArray);
    }
}
