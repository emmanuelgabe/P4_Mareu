package com.test.example.maru.ui.meeting_list;

import android.annotation.SuppressLint;
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
    @BindView(R.id.activity_main_list_detail)
    FrameLayout mDetailView;
    @BindView(R.id.activity_main_meeting_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_add_fab)
    FloatingActionButton mAddFab;
    @BindView(R.id.fragment_filter)
    View fragmentFilter;
    MenuItem mfilterMenuItem;
    Boolean isfilter;
    private List<Meeting> mMeetingList;
    private boolean isTablet = false;
    private MeetingApiService mApiService;

/*

    @BindView(R.id.list_meeting)
    private RecyclerView mRecyclerView;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeeting();

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mfilterMenuItem = menu.findItem(R.id.filterbtn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.filterbtn) {
            if (isfilter == false) {

                if (isTablet) {
                    mDetailView.animate().setDuration(1500).translationY(140);
                    fragmentFilter.animate().setDuration(1500).translationY(140);
                    mRecyclerView.animate().setDuration(1500).translationY(140);
                } else {
                    fragmentFilter.animate().setDuration(1500).translationY(200);
                    mRecyclerView.animate().setDuration(1500).translationY(200);
                }
                isfilter = true;
            } else {
                closeFileter();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    //TODO SourceLockedOrientationActivity
    @SuppressLint("SourceLockedOrientationActivity")
    private void initViews() {
        isfilter = false;
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mRecyclerView.setAdapter(new MeetingRecyclerViewAdapter(mMeetingList, this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        if (isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getSupportActionBar().setTitle("Maréu"); // TODO titre non inscrit en mode tablette
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        FloatingActionButton fab = findViewById(R.id.activity_main_add_fab);
        fab.setOnClickListener(view -> {
            if (!isTablet) {
                closeFileter();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.activity_main_list_detail, new AddMeetingFragment());
                ft.addToBackStack(null); //TODO ?
                ft.commit();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mAddFab.hide();
                mDetailView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mfilterMenuItem.setVisible(false);
            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.activity_main_list_detail, new AddMeetingFragment());
                ft.addToBackStack(null); //TODO ?
                ft.commit();
                mDetailView.setVisibility(View.VISIBLE);
            }


        });
        mToolbar.setNavigationOnClickListener(v -> {
            mfilterMenuItem.setVisible(true);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(getSupportFragmentManager().findFragmentById(R.id.activity_main_list_detail)).commit();
            mAddFab.show();
            mRecyclerView.setVisibility(View.VISIBLE);
            mDetailView.setVisibility(View.INVISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        });
    }

    @Override
    public void onItemHolderClick(int position) {
        if (!isTablet) {
            mfilterMenuItem.setVisible(false);
            mDetailView.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mAddFab.hide();
            mRecyclerView.setVisibility(View.INVISIBLE);
            closeFileter();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main_list_detail, DetailMeetingFragment.newInstance(position));
        ft.commit();
    }

    private void closeFileter() {
        fragmentFilter.animate().setDuration(1500).translationY(0);
        mRecyclerView.animate().setDuration(1500).translationY(0);
        if (isTablet) mDetailView.animate().setDuration(1500).translationY(0);
        isfilter = false;

    }
}
