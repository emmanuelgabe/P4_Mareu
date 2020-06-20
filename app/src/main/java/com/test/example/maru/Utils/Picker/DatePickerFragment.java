package com.test.example.maru.Utils.Picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.test.example.maru.ui.meeting.add.AddMeetingActivity;

import java.util.Calendar;

import static com.test.example.maru.ui.meeting.add.AddMeetingActivity.DATE_MEETING_TAG;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
String tag;

    public DatePickerFragment(String tag) {
        this.tag = tag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (tag == DATE_MEETING_TAG) datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        OnDateSetListener listener = (OnDateSetListener) requireActivity();
        listener.onDateSet(tag,view,year,month,dayOfMonth);
    }

    public interface OnDateSetListener{
        void onDateSet(String tag,DatePicker view,int year,int month,int dayOfMonth);
    }
}
