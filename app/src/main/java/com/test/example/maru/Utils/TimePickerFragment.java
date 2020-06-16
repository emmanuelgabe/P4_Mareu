package com.test.example.maru.Utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.test.example.maru.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    String tag;

    public TimePickerFragment(String tag) {
        this.tag = tag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int hourOfDay = 0;
        if (tag == "DurationPicker")  hourOfDay = 0;
        else if (tag == "HourPicker") hourOfDay = 12;
        TimePickerDialog mTimePickerDialog = new TimePickerDialog(getActivity(), R.style.MyDialogTheme, this, hourOfDay, 0, true);
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
