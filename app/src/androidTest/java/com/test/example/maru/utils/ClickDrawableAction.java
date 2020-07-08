package com.test.example.maru.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.Matchers.allOf;

public class ClickDrawableAction implements ViewAction
    {
        public static final int Left = 0;
        public static final int Top = 1;
        public static final int Right = 2;
        public static final int Bottom = 3;

        @Location
        private final int drawableLocation;

        public ClickDrawableAction(@Location int drawableLocation)
        {
            this.drawableLocation = drawableLocation;
        }

        @Override
        public Matcher<View> getConstraints()
        {
            return allOf(isAssignableFrom(TextInputLayout.class), new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class)
            {
                @Override protected boolean matchesSafely(TextInputLayout til) {
                    //get focus so drawables are visible and if the textview has a drawable in the position then return a match
                    return til.requestFocusFromTouch() && til.getEditText().getCompoundDrawables()[drawableLocation] != null;
                }

                @Override
                public void describeTo(Description description)
                {
                    description.appendText("has drawable");
                }
            });
        }

        @Override
        public String getDescription()
        {
            return "click drawable ";
        }

        @Override
        public void perform(final UiController uiController, final View view)
        {
            TextInputLayout til = (TextInputLayout)view;//we matched
            if(til != null && til.requestFocusFromTouch())//get focus so drawables are visible
            {
                //get the bounds of the drawable image
                Rect drawableBounds = til.getEditText().getCompoundDrawables()[drawableLocation].getBounds();

                //calculate the drawable click location for left, top, right, bottom
                final Point[] clickPoint = new Point[4];
                clickPoint[Left] = new Point(til.getLeft() + (drawableBounds.width() / 2), (int)(til.getPivotY() + (drawableBounds.height() / 2)));
                clickPoint[Top] = new Point((int)(til.getPivotX() + (drawableBounds.width() / 2)), til.getTop() + (drawableBounds.height() / 2));
                clickPoint[Right] = new Point(til.getRight() + (drawableBounds.width() / 2), (int)(til.getPivotY() + (drawableBounds.height() / 2)));
                clickPoint[Bottom] = new Point((int)(til.getPivotX() + (drawableBounds.width() / 2)), til.getBottom() + (drawableBounds.height() / 2));

                if(til.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, clickPoint[drawableLocation].x, clickPoint[drawableLocation].y, 0)))
                    til.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, clickPoint[drawableLocation].x, clickPoint[drawableLocation].y, 0));
            }
        }

        @IntDef({ Left, Top, Right, Bottom })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Location{}
    }

