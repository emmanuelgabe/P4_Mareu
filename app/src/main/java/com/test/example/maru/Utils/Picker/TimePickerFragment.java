package com.test.example.maru.Utils.Picker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.test.example.maru.R;

import static com.test.example.maru.ui.meeting.add.AddMeetingActivity.DURATION_TIME_TAG;
import static com.test.example.maru.ui.meeting.add.AddMeetingActivity.HOUR_MEETING_TAG;

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
        if (tag == DURATION_TIME_TAG) {
            hourOfDay = 0;
            title = "Durée de la réunion";
        } else if (tag == HOUR_MEETING_TAG) {
            hourOfDay = 12;
            title = "Heure de la réunion";
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
