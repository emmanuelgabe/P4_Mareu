package com.test.example.maru.ui.meeting_list;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.test.example.maru.R;

import butterknife.BindView;

public class AddMeetingActivity extends AppCompatActivity {
    @BindView(R.id.fragment_add_meeting_start_meeting_date_til) TextInputLayout test;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        initview();
    }

    private void initview() {
        if (isTablet) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}