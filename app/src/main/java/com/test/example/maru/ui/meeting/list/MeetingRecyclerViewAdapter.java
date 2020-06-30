package com.test.example.maru.ui.meeting.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.Utils.DateTimeUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private List<Meeting> meetings;
    private OnMeetingClickListener listener;
    private Context mContext;

    public MeetingRecyclerViewAdapter(List<Meeting> meetings, OnMeetingClickListener listener,Context context) {
        this.meetings = meetings;
        this.listener = listener;
        this.mContext = context;

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
        Meeting meeting = meetings.get(position);
        holder.mEmails.setText(meeting.getEmails()
                .replace(",", "\n")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", ""));
        holder.mMeetingInfo.setText(meeting.getSubject() + " - " + meeting.getRoom());
        holder.mMeetingDate.setText(DateTimeUtils.getStringTimeDateInformations(meeting.getStartTime(), meeting.getEndTime(),mContext));
        holder.mDeleteButton.setOnClickListener(v -> listener.onMeetingDelete(meeting));
        int colorposition = Arrays.asList(holder.mViewShape.getResources().getStringArray(R.array.room_array)).indexOf(meeting.getRoom());
        String[] color = holder.mViewShape.getResources().getStringArray(R.array.room_color);
        GradientDrawable backgroundGradient = (GradientDrawable) holder.mViewShape.getBackground();
        backgroundGradient.setColor(Color.parseColor((color[colorposition])));
        holder.itemView.setSelected(selectedPos == position);
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }


    public interface OnMeetingClickListener {
        void onMeetingDelete(Meeting meeting);

        void onMeetingItemHolderClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_holder) public View itemHolder;
        @BindView(R.id.item_meeting_btn_delete) public ImageButton mDeleteButton;
        @BindView(R.id.item_meeting_tv_email) public TextView mEmails;
        @BindView(R.id.item_meeting_tv_meeting_info) TextView mMeetingInfo;
        @BindView(R.id.item_meeting_tv_date) TextView mMeetingDate;
        @BindView(R.id.item_meeting_view_shape) View mViewShape;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getResources().getBoolean(R.bool.is_tablet)) {
                notifyItemChanged(selectedPos);
                selectedPos = getLayoutPosition();
                notifyItemChanged(selectedPos);
            }
            listener.onMeetingItemHolderClick(getAdapterPosition());
        }
    }
}
