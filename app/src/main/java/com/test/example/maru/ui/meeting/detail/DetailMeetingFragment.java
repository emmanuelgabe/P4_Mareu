package com.test.example.maru.ui.meeting.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.example.maru.DI.DI;
import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.Utils.DateTimeUtils;
import com.test.example.maru.service.MeetingApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class DetailMeetingFragment extends Fragment {
    public static final String KEY_MEETING_POSITION = "MEETING_POSITION";
    @BindView(R.id.fragment_detail_meeting__tv_subject) TextView mSubjectTv;
    @BindView(R.id.fragment_detail_meeting_tv_room) TextView mRoomTv;
    @BindView(R.id.fragment_detail_meeting_tv_emails) TextView mEmailsTv;
    @BindView(R.id.fragment_detail_meeting_tv_time) TextView mTimeTv;
    private int position;

    public static DetailMeetingFragment newInstance(int position) {
        DetailMeetingFragment detailMeetingFragment = new DetailMeetingFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_MEETING_POSITION, position);
        detailMeetingFragment.setArguments(args);
        return detailMeetingFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        position = getArguments().getInt(KEY_MEETING_POSITION, 1);
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

        MeetingApiService mApiService = DI.getMeetingApiService();
        Meeting mMeeting = mApiService.getMeeting().get(position);
        mTimeTv.setText(DateTimeUtils.getStringTimeDateInformations(mMeeting.getStartTime(),mMeeting.getEndTime()));
        mRoomTv.setText(mMeeting.getRoom());
        mSubjectTv.setText(mMeeting.getSubject());
        mEmailsTv.setText(mMeeting.getEmails());
    }

}
