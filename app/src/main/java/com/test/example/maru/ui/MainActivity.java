package com.test.example.maru.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.test.example.maru.DI.DI;
import com.test.example.maru.Model.Meeting;
import com.test.example.maru.R;
import com.test.example.maru.Utils.Picker.DatePickerFragment;
import com.test.example.maru.service.MeetingApiService;
import com.test.example.maru.ui.meeting.add.AddMeetingActivity;
import com.test.example.maru.ui.meeting.detail.DetailMeetingFragment;
import com.test.example.maru.ui.meeting.list.MeetingRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MeetingRecyclerViewAdapter.OnMeetingClickListener, DatePickerFragment.OnDateSetListener {
    public static final String START_FILTER_TAG = "StartDateMeeting";
    public static final String END_FILTER_TAG = "EndDateMeeting";

    @BindView(R.id.activity_main_fl_detail) FrameLayout mDetailView;
    @BindView(R.id.activity_main_recyclerview_meetings) RecyclerView mRecyclerView;
    @BindView(R.id.activity_main__fab_add) FloatingActionButton mAddFab;
    @BindView(R.id.content_filter_view) View mFilter;
    @BindView(R.id.content_filter_til_meeting_after_date) TextInputLayout mStartDate;
    @BindView(R.id.content_filte_til_meeting_befor_date) TextInputLayout mEndDate;
    @BindView(R.id.content_filter_actv_room) AutoCompleteTextView mRoomACTV;
    MenuItem mFilterMenuItem;
    Boolean mFilterIsOpen = false;
    private List<Meeting> mMeetingList;
    private boolean isTablet;
    private MeetingApiService mApiService;
    private MeetingRecyclerViewAdapter mMeetingsAdapter;
    private List<Meeting> mMeetingListFiltered;
    private Long filterMinDate;
    private Long filterMaxDate;
    private String filterRoom;
    private int mRecyclerViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeetings();
        mMeetingListFiltered = new ArrayList<>();
        mMeetingListFiltered.addAll(mMeetingList);
        filterMinDate = Long.MIN_VALUE;
        filterMaxDate = Long.MAX_VALUE;
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mFilterMenuItem = menu.findItem(R.id.filter_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_item) {
            if (mFilterIsOpen) {
                closeFilter();
            } else {
                // TODO add listener in annimation and change size when finish and when start :
          /*      ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
                params.height =  mRecyclerView.getHeight() - getResources().getDimensionPixelSize(R.dimen.filter_translationY);
                mRecyclerView.setLayoutParams(params);*/

                mFilter.animate().setDuration(1000).translationY(getResources().getDimensionPixelSize(R.dimen.filter_translationY));
                //   mRecyclerView.animate().setDuration(1000).translationY(getResources().getDimensionPixelSize(R.dimen.filter_translationY));

                changeRecyclerviewSize(mRecyclerView.getHeight() - getResources().getDimensionPixelSize(R.dimen.filter_translationY));
                if (isTablet) {
                    mDetailView.animate().setDuration(1000).translationY(getResources().getDimensionPixelSize(R.dimen.filter_translationY));
                }
                mFilterIsOpen = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    private void initViews() {
        if (isTablet) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mMeetingsAdapter = new MeetingRecyclerViewAdapter(mMeetingListFiltered, this,this);
        mRecyclerView.setAdapter(mMeetingsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAddFab.setOnClickListener(view -> {
            if (mFilterIsOpen)  closeFilter();
            Intent startAddMeetingActivity = new Intent(this, AddMeetingActivity.class);
            startActivity(startAddMeetingActivity);
        });
        mStartDate.getEditText().setOnClickListener(v -> {
            DatePickerFragment mDatePickerFragment = new DatePickerFragment(START_FILTER_TAG);
            mDatePickerFragment.show(getSupportFragmentManager(), START_FILTER_TAG);
        });
        mEndDate.getEditText().setOnClickListener(v -> {
            DatePickerFragment mDatePickerFragment = new DatePickerFragment(END_FILTER_TAG);
            mDatePickerFragment.show(getSupportFragmentManager(), END_FILTER_TAG);
        });
        String[] room = getResources().getStringArray(R.array.room_array);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, room);
        mRoomACTV.setAdapter(roomAdapter);
        mRoomACTV.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterRoom = mRoomACTV.getText().toString();
                filter();
            }
        });
        mStartDate.getEditText().addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    filterMinDate = Long.MIN_VALUE;
                    filter();
                }
            }
        });
        mEndDate.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    filterMaxDate = Long.MAX_VALUE;
                    filter();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        closeFragment();
        return false;
    }

    @Override
    protected void onRestart() {
        mEndDate.getEditText().setText("");
        mStartDate.getEditText().setText("");
        mRoomACTV.setText("");
        mMeetingListFiltered.clear();
        mMeetingListFiltered.addAll(mMeetingList);
        initViews();
        super.onRestart();
    }

    @Override
    public void onMeetingDelete(Meeting meeting) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.dialog_delete_meeting_message);
        alertDialogBuilder.setPositiveButton(R.string.dialog_button_yes, (dialog, which) -> {
            MeetingApiService mApiService = DI.getMeetingApiService();
            mApiService.deleteMeeting(meeting);
            mMeetingListFiltered.remove(meeting);
            if (isTablet) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(getSupportFragmentManager().findFragmentById(R.id.activity_main_fl_detail)).commit();
            }
            initViews();
        });
        alertDialogBuilder.setNegativeButton(R.string.dialog_button_no, (dialog, which) -> {
            if (dialog != null) dialog.dismiss();
        });
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onMeetingItemHolderClick(int position) {
        if (!isTablet) {
            mFilterMenuItem.setVisible(false);
            mDetailView.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mAddFab.hide();
            mRecyclerView.setVisibility(View.INVISIBLE);
            getSupportActionBar().setTitle(R.string.activity_main_actionbar_title_fragment_detail);
           if (mFilterIsOpen)  closeFilter();
        } //else mAddFab.show();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main_fl_detail, DetailMeetingFragment.newInstance(mMeetingListFiltered.get(position))).commit();
    }

    private void closeFragment() {
        getSupportActionBar().setTitle(R.string.activity_main_actionbar_title);
        mFilterMenuItem.setVisible(true);
        mAddFab.show();
        mRecyclerView.setVisibility(View.VISIBLE);
        mDetailView.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    private void closeFilter() {
        changeRecyclerviewSize(mRecyclerView.getHeight() + getResources().getDimensionPixelSize(R.dimen.filter_translationY));
        mFilter.animate().setDuration(1000).translationY(0);
        // mRecyclerView.animate().setDuration(1000).translationY(0);
        if (isTablet) mDetailView.animate().setDuration(1000).translationY(0);
        mFilterIsOpen = false;
    }

    @Override
    public void onBackPressed() {
        if (mDetailView.isShown() && !isTablet) closeFragment();
        else super.onBackPressed();
    }

    @Override
    public void onDateSet(String tag, DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.date_format), Locale.FRENCH);
        if (tag == START_FILTER_TAG) {
            mStartDate.getEditText().setText(df.format(c.getTime()));
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 1);
            filterMinDate = c.getTimeInMillis();
            filter();
        } else if (tag == END_FILTER_TAG) {
            mEndDate.getEditText().setText(df.format(c.getTime()));
            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 58);
            filterMaxDate = c.getTimeInMillis();
            filter();
        }
    }

    public void filter() {
        mMeetingListFiltered.clear();
        for (Meeting meeting : mMeetingList) {
            if (meeting.getStartTime() > filterMinDate
                    && meeting.getStartTime() < filterMaxDate
                    && ((filterRoom == null || filterRoom.length() == 0) || (meeting.getRoom().toLowerCase().contains(filterRoom.toLowerCase().trim()))))
                mMeetingListFiltered.add(meeting);
        }
        mMeetingsAdapter.notifyDataSetChanged();
    }

    public void changeRecyclerviewSize(int size) {
        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(mRecyclerView.getHeight(), size)
                .setDuration(1000);
        slideAnimator.addUpdateListener(animation1 -> {
            Integer value = (Integer) animation1.getAnimatedValue();
            mRecyclerView.getLayoutParams().height = value.intValue();
            mRecyclerView.requestLayout();
        });
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.play(slideAnimator);
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override public void onAnimationEnd(Animator animation) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            @Override public void onAnimationCancel(Animator animation) {}
            @Override public void onAnimationRepeat(Animator animation) {}
        });
        animationSet.start();
    }
}
