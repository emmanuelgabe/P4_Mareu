package com.test.example.maru.ui.meeting.list;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> /*implements Filterable */ {
    private List<Meeting> meetings;
    private OnMeetingClickListener listener;
/*    private List<Meeting> meetingsFull;
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Meeting> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(meetingsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Meeting item : meetingsFull) {
                    if (item.getRoom().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            meetings.clear();
            meetings.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };*/

    public MeetingRecyclerViewAdapter(List<Meeting> meetings, OnMeetingClickListener listener) {
        this.meetings = meetings;
        this.listener = listener;
        /*   meetingsFull = new ArrayList<>(meetings);*/
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
        holder.mMeetingDate.setText(DateTimeUtils.getStringTimeDateInformations(meeting.getStartTime(), meeting.getEndTime()));
        holder.mDeleteButton.setOnClickListener(v -> listener.onMeetingDelete(meeting));
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

  /*  @Override
    public Filter getFilter() {
        return filter;
    }*/

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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onMeetingItemHolderClick(getAdapterPosition());
        }
    }

}
