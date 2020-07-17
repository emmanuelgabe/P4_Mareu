package com.test.example.mareu.UI.meeting.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.example.mareu.Model.Meeting;
import com.test.example.mareu.R;
import com.test.example.mareu.Utils.DateTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.test.example.mareu.Utils.EmailStringUtils.formatEmails;
import static com.test.example.mareu.Utils.EmailStringUtils.getEmailsSplit;


public class DetailMeetingFragment extends Fragment {
    public static final String KEY_MEETING = "MEETING_POSITION";
    public static Meeting sMeeting;
    @BindView(R.id.fragment_detail_meeting_tv_subject) TextView mSubjectTv;
    @BindView(R.id.fragment_detail_meeting_tv_room) TextView mRoomTv;
    @BindView(R.id.fragment_detail_meeting_tv_emails) TextView mEmailsTv;
    @BindView(R.id.fragment_detail_meeting_tv_time) TextView mTimeTv;
    @BindView(R.id.fragment_detail_meeting_tv_emails2) TextView mEmailsTv2;

    public static DetailMeetingFragment newInstance(Meeting meeting) {
        DetailMeetingFragment detailMeetingFragment = new DetailMeetingFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_MEETING, meeting);
        detailMeetingFragment.setArguments(args);
        return detailMeetingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sMeeting = getArguments().getParcelable(KEY_MEETING);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_meeting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTimeTv.setText(DateTimeUtils.getStringTimeDateInformation(sMeeting.getStartTime(), sMeeting.getEndTime(), getContext()));
        mRoomTv.setText(sMeeting.getRoom());
        mSubjectTv.setText(sMeeting.getSubject());
        if (getResources().getBoolean(R.bool.is_tablet)) {
            mEmailsTv.setText(getEmailsSplit(1, sMeeting.getEmails()));
            mEmailsTv2.setText(getEmailsSplit(2, sMeeting.getEmails()));
        } else {
            mEmailsTv.setText(formatEmails(sMeeting.getEmails()));
        }
    }
}