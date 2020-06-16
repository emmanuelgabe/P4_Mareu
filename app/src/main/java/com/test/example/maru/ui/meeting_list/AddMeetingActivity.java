package com.test.example.maru.ui.meeting_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.test.example.maru.R;
import com.test.example.maru.Utils.DatePickerFragment;
import com.test.example.maru.Utils.TimePickerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerFragment.OnTimeSetListener {
    @BindView(R.id.activity_add_meeting_cancel_btn) Button mCancelBtn;
    @BindView(R.id.activity_add_meeting_validate_btn) Button mvalidateBtn;
    @BindView(R.id.fragment_add_meeting_duration_til) TextInputLayout mDurationTil;
    @BindView(R.id.activity_add_meeting_date_til) TextInputLayout mDateTil;
    @BindView(R.id.activity_add_meeting_hour_til) TextInputLayout mHourTil;
    @BindView(R.id.activity_add_meeting_email_til) TextInputLayout mEmailTil;
    @BindView(R.id.activity_add_meeting_subject_til) TextInputLayout mSubjectTil;
    @BindView(R.id.activity_add_meeting_subject_tie) TextInputEditText mSubjecttie;
    @BindView(R.id.fragment_add_meeting_room_spinner) Spinner mRoomSp;

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
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ajouter une réunion");

        if (isTablet) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDateTil.getEditText().setOnClickListener(v -> {
            DatePickerFragment mDatePickerFragment = new DatePickerFragment();
            mDatePickerFragment.show(getSupportFragmentManager(), "datePicker");
        });
        mHourTil.getEditText().setOnClickListener(v -> {
            TimePickerFragment mTimePickerFragment = new TimePickerFragment("HourPicker");
            mTimePickerFragment.show(getSupportFragmentManager(), "HourPicker");
        });

        mDurationTil.getEditText().setOnClickListener(v -> {
            TimePickerFragment mTimePickerFragment = new TimePickerFragment("DurationPicker");
            mTimePickerFragment.show(getSupportFragmentManager(), "DurationPicker");
        });
        mSubjectTil.getEditText().setOnClickListener(v -> {
            EditText edittext = new EditText(this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Sujet de la réunion");
            alertDialogBuilder.setView(edittext);
            alertDialogBuilder.setPositiveButton("Ok", (dialog, which) -> {
                mSubjecttie.setText(edittext.getText());
                if (dialog != null) dialog.dismiss();
                closeKeyboard();
            });
            alertDialogBuilder.setNegativeButton("Annuler", (dialog, which) -> {
                if (dialog != null) dialog.dismiss();
                closeKeyboard();
            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
            openKeyboard();
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        mDateTil.getEditText().setText(df.format(c.getTime()));
        //TODO save date
    }

    @Override
    public void onTimeSet(String tag, TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.FRENCH);
        if (tag == "HourPicker") {
            mHourTil.getEditText().setText(df.format(c.getTime()));
            //TODO save start hour
        } else if (tag == "DurationPicker") {
            mDurationTil.getEditText().setText(df.format(c.getTime()));
            //TODO save meeting duration
        }
    }

    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
    }



}