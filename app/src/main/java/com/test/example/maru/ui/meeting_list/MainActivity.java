package com.test.example.maru.ui.meeting_list;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.example.maru.DI.DI;
import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.service.MeetingApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MeetingRecyclerViewAdapter.OnMeetingClickListener {
    @BindView(R.id.activity_main_list_detail) FrameLayout mDetailView;
    @BindView(R.id.activity_main_meeting_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_add_fab) FloatingActionButton mAddFab;
    @BindView(R.id.content_filter_view) View mFilter;
    MenuItem mFilterMenuItem;
    MenuItem mClearMenuItem;
    Boolean mFilterIsOpen = false;
    private List<Meeting> mMeetingList;
    private boolean isTablet;
    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeeting();
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mFilterMenuItem = menu.findItem(R.id.filter_item);
        mClearMenuItem = menu.findItem(R.id.clear_filter_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_item) {
            if (mFilterIsOpen) {
                closeFilter();
            } else {
                if (isTablet) {
                    mDetailView.animate().setDuration(1000).translationY(140);
                    mFilter.animate().setDuration(1000).translationY(140);
                    mRecyclerView.animate().setDuration(1000).translationY(140);
                } else {
                    mFilter.animate().setDuration(1000).translationY(200);
                    mRecyclerView.animate().setDuration(1000).translationY(200);
                }
                mFilterIsOpen = true;
            }
        } else if (item.getItemId() == R.id.clear_filter_item) {
            //TODO clear filter
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        if (isTablet) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mRecyclerView.setAdapter(new MeetingRecyclerViewAdapter(mMeetingList, this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAddFab.setOnClickListener(view -> {
            Intent startAddMeetingActivity = new Intent(this, AddMeetingActivity.class);
            startActivity(startAddMeetingActivity);
            closeFilter();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        closeFragment();
        return false;
    }
    @Override
    public void onMeetingDelete(Meeting meeting) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Êtes-vous sûr(e) de vouloir supprimer cette réunion ? ");
        alertDialogBuilder.setPositiveButton("Oui", (dialog, which) -> {
            MeetingApiService mApiService = DI.getMeetingApiService();
            mApiService.deleteMeeting(meeting);
            if (isTablet) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(getSupportFragmentManager().findFragmentById(R.id.activity_main_list_detail)).commit();
            }
            initViews();
        });
        alertDialogBuilder.setNegativeButton("Non", (dialog, which) -> {
            if (dialog != null) dialog.dismiss();
        });
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onMeetingItemHolderClick(int position) {
        if (!isTablet) {
            mFilterMenuItem.setVisible(false);
            mClearMenuItem.setVisible(false);
            mDetailView.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mAddFab.hide();
            mRecyclerView.setVisibility(View.INVISIBLE);
            closeFilter();
        } else mAddFab.show();
            getSupportActionBar().setTitle("Détail de la réunion");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main_list_detail, DetailMeetingFragment.newInstance(position)).commit();
    }

    private void closeFragment() {
        getSupportActionBar().setTitle("Maréu");
        mFilterMenuItem.setVisible(true);
        mClearMenuItem.setVisible(true);
        mAddFab.show();
        mRecyclerView.setVisibility(View.VISIBLE);
        mDetailView.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    private void closeFilter() {
        mFilter.animate().setDuration(1000).translationY(0);
        mRecyclerView.animate().setDuration(1000).translationY(0);
        if (isTablet) mDetailView.animate().setDuration(1000).translationY(0);
        mFilterIsOpen = false;
    }

    @Override
    public void onBackPressed() {
        if (mDetailView.isShown() && !isTablet) closeFragment();
        else super.onBackPressed();
    }
}
