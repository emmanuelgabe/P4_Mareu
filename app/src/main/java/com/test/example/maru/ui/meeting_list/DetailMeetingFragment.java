package com.test.example.maru.ui.meeting_list;

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
import com.test.example.maru.service.MeetingApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMeetingFragment extends Fragment {
    @BindView(R.id.fragment_detail_meeting_subject)
    TextView mSubjectTv;
    @BindView(R.id.fragment_detail_meeting_room)
    TextView mRoomTv;
    @BindView(R.id.fragment_detail_meeting_emails)
    TextView mEmailsTv;
    @BindView(R.id.fragment_detail_meeting_time)
    TextView mTimeTv;
    private String time;
    private String room;
    private String subject;
    private String emails;
    private int position;

    public static DetailMeetingFragment newInstance(int position) {
        DetailMeetingFragment detailMeetingFragment = new DetailMeetingFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        detailMeetingFragment.setArguments(args);

        return detailMeetingFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        position = getArguments().getInt("position", 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_detail_meeting, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MeetingApiService mApiService = DI.getMeetingApiService();
        Meeting mMeeting = mApiService.getMeeting().get(position);
        mTimeTv.setText(mMeeting.getStartTime());
        mRoomTv.setText(mMeeting.getRoom());
        mSubjectTv.setText(mMeeting.getSubject());
        mEmailsTv.setText(mMeeting.getEmails());
    }

}
