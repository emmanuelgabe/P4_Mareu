package com.test.example.mareu.Utils;

import android.content.Context;

import com.test.example.mareu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String getStringTimeDateInformation(Long startDateMillis, Long endDateMillis, Context ctx) {
        SimpleDateFormat startDateDf = new SimpleDateFormat(ctx.getResources().getString(R.string.date_format), Locale.FRANCE);
        SimpleDateFormat startHourDf = new SimpleDateFormat(ctx.getResources().getString(R.string.hour_format), Locale.FRANCE);
        Date startDate = new Date(startDateMillis);

        SimpleDateFormat endDf = new SimpleDateFormat(ctx.getResources().getString(R.string.hour_format), Locale.FRANCE);
        Date endDate = new Date(endDateMillis);

        return "Le " + startDateDf.format(startDate) + " de " + startHourDf.format(startDate) + " à " + endDf.format(endDate);
    }
}