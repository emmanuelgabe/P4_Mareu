package com.test.example.maru.ui.meeting_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;
    private OnMeetingClickListener mListener;

    public MeetingRecyclerViewAdapter(List<Meeting> meetings, OnMeetingClickListener listener) {
        mMeetings = meetings;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
       holder.mEmails.setText(meeting.getEmails());
      holder.mMeetingInfo.setText(meeting.getSubject() + " - " + meeting.getStartTime() + " - " + meeting.getRoom());
     //TODO   holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public interface OnMeetingClickListener {
        void onItemHolderClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


@BindView(R.id.item_holder)
        public View itemHolder;
        @BindView(R.id.item_meeting_delete_button)
        public ImageButton mDeleteButton;
        @BindView(R.id.item_meeting_email_tv)
        public TextView mEmails;
        @BindView(R.id.item_meeting_meetinginfo_tv)
        TextView mMeetingInfo;

        public ViewHolder(View view) {
                      super(view);
        ButterKnife.bind(this,view);
          itemHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemHolderClick(getAdapterPosition());
        }
    }

}
