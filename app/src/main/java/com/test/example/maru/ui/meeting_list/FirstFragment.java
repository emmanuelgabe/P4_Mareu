/*
package com.test.example.maru.ui.meeting_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.example.maru.DI.DI;
import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.service.MeetingApiService;

import java.util.List;

public class FirstFragment extends Fragment implements MeetingRecyclerViewAdapter.OnMeetingClickListener{
    private List<Meeting> mMeetingList;
    private MeetingApiService mApiService;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void initList() {
        mMeetingList = mApiService.getMeeting();
        mRecyclerView.setAdapter(new MeetingRecyclerViewAdapter(mMeetingList, this));
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
  */
/*      //View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //TODO check
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;*//*


//
//    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemHolderClick(int position) {


    }
}
*/
