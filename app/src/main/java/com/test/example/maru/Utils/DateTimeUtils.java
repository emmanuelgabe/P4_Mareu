package com.test.example.maru.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String getStringTimeDateInformations(Long startDateMillis, Long endDateMillis) {
        SimpleDateFormat startDateDf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
        SimpleDateFormat startHourDf = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        Date startdate = new Date(startDateMillis);

        SimpleDateFormat endDf = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        Date endtdate = new Date(endDateMillis);

        String timeDateInforamtion = "Le " + startDateDf.format(startdate) + " de " + startHourDf.format(startdate) + " à " + endDf.format(endtdate);
        return timeDateInforamtion;
    }
}
