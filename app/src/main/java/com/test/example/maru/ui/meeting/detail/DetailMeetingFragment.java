package com.test.example.maru.ui.meeting.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.Utils.DateTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMeetingFragment extends Fragment {
    public static final String KEY_MEETING = "MEETING_POSITION";
    @BindView(R.id.fragment_detail_meeting__tv_subject) TextView mSubjectTv;
    @BindView(R.id.fragment_detail_meeting_tv_room) TextView mRoomTv;
    @BindView(R.id.fragment_detail_meeting_tv_emails) TextView mEmailsTv;
    @BindView(R.id.fragment_detail_meeting_tv_time) TextView mTimeTv;
    private Meeting meeting;

    public static DetailMeetingFragment newInstance(Meeting meeting) {
        DetailMeetingFragment detailMeetingFragment = new DetailMeetingFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_MEETING, meeting);
        detailMeetingFragment.setArguments(args);
        return detailMeetingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        meeting = getArguments().getParcelable(KEY_MEETING);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_detail_meeting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTimeTv.setText(DateTimeUtils.getStringTimeDateInformations(meeting.getStartTime(), meeting.getEndTime()));
        mRoomTv.setText(meeting.getRoom());
        mSubjectTv.setText(meeting.getSubject());
        mEmailsTv.setText(meeting.getEmails()
                .replace(",", "\n")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", ""));
    }
}
