package com.test.example.mareu.Utils.Picker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.test.example.mareu.R;

import static com.test.example.mareu.UI.meeting.add.AddMeetingActivity.DURATION_TIME_TAG;
import static com.test.example.mareu.UI.meeting.add.AddMeetingActivity.HOUR_MEETING_TAG;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    String tag;

    public TimePickerFragment(String tag) {
        this.tag = tag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int hourOfDay = 0;
        String title = null;
        if (tag.equals(DURATION_TIME_TAG)) {
            hourOfDay = 1;
            title = getString(R.string.dialog_timepicker_title_duration);
        } else if (tag.equals(HOUR_MEETING_TAG)) {
            hourOfDay = 12;
            title = getString(R.string.dialog_timepicker_title_hour);
        }
        TimePickerDialog mTimePickerDialog = new TimePickerDialog(getActivity(), R.style.MyDialogTheme, this, hourOfDay, 0, true);
        mTimePickerDialog.setTitle(title);
        return mTimePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        OnTimeSetListener listener = (OnTimeSetListener) requireActivity();
        listener.onTimeSet(tag, view, hourOfDay, minute);
    }

    public interface OnTimeSetListener {
        void onTimeSet(String tag, TimePicker view, int hourOfDay, int minute);
    }

}
