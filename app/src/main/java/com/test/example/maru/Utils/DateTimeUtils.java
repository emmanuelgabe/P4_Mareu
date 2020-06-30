package com.test.example.maru.Utils;

import android.content.Context;

import com.test.example.maru.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String getStringTimeDateInformations(Long startDateMillis, Long endDateMillis, Context ctx) {
        SimpleDateFormat startDateDf = new SimpleDateFormat(ctx.getResources().getString(R.string.date_format), Locale.FRANCE);
        SimpleDateFormat startHourDf = new SimpleDateFormat(ctx.getResources().getString(R.string.hour_format), Locale.FRANCE);
        Date startdate = new Date(startDateMillis);

        SimpleDateFormat endDf = new SimpleDateFormat(ctx.getResources().getString(R.string.hour_format), Locale.FRANCE);
        Date endtdate = new Date(endDateMillis);

        String timeDateInforamtion = "Le " + startDateDf.format(startdate) + " de " + startHourDf.format(startdate) + " à " + endDf.format(endtdate);
        return timeDateInforamtion;
    }
}
