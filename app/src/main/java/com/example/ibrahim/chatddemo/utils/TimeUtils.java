package com.example.ibrahim.chatddemo.utils;


import android.content.Context;
import android.support.annotation.NonNull;


import com.example.ibrahim.chatddemo.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * By katepratik on 11/8/16.
 */
public class TimeUtils {

    public static String getTimeZone() {
        String defaultTimeZone = "IST";
        if (BuildConfig.DEBUG)
            return defaultTimeZone;
        else {
            TimeZone timezone = TimeZone.getDefault();
            String tzone = timezone.getDisplayName(false, TimeZone.SHORT);
            DebugUtils.log("Time Zone : " + tzone);
            if (tzone.matches("[a-zA-Z]+"))
                return tzone;
            else
                return defaultTimeZone;
        }
    }


    private static int getTimeZoneOffset() {
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        return mTimeZone.getRawOffset();
    }



    public static String getTodaysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getTimePretty(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    public static String getDateTimePretty(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, hh:mm a", Locale.US);
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    @NonNull
    public static String timeEscaped(long time) {
        long current = System.currentTimeMillis();
        long difference = (current - time);

        if (difference < (60 * 1000)) {
            long diffSeconds = difference / 1000 % 60;
            if (difference > 0)
                return "" + diffSeconds + " seconds ago";
            else
                return "just now";
        } else if (difference < (60 * 60 * 1000)) {
            long diffMinutes = difference / (60 * 1000) % 60;
            if (diffMinutes == 1)
                return "" + diffMinutes + " minute ago";
            else
                return "" + diffMinutes + " minutes ago";
        } else if (difference < (24 * 60 * 60 * 1000)) {
            long diffHours = difference / (60 * 60 * 1000) % 24;
            if (diffHours == 1)
                return "" + diffHours + " hour ago";
            else
                return "" + diffHours + " hours ago";
        } else if (difference < (7 * 24 * 60 * 60 * 1000)) {
            long diffDays = difference / (24 * 60 * 60 * 1000);
            if (diffDays == 1)
                return "" + diffDays + " day ago";
            else
                return "" + diffDays + " days ago";
        } else {
            long diffWeeks = difference / (7 * 24 * 60 * 60 * 1000);
            if (diffWeeks == 1)
                return "" + diffWeeks + " week ago";
            else
                return "" + diffWeeks + " weeks ago";
        }

    }

    @NonNull
    public static String timeEscapedShort(long time) {
        long current = System.currentTimeMillis();
        long difference = (current - time);

        if (difference < (60 * 1000)) {
            long diffSeconds = difference / 1000 % 60;
            if (difference > 0)
                return "" + diffSeconds + "s";
            else
                return "" + 0 + "s";
        } else if (difference < (60 * 60 * 1000)) {
            long diffMinutes = difference / (60 * 1000) % 60;
            return "" + diffMinutes + "m";
        } else if (difference < (24 * 60 * 60 * 1000)) {
            long diffHours = difference / (60 * 60 * 1000) % 24;
            return "" + diffHours + "h";
        } else if (difference < (7 * 24 * 60 * 60 * 1000)) {
            long diffDays = difference / (24 * 60 * 60 * 1000);
            return "" + diffDays + "d";
        } else {
            long diffWeeks = difference / (7 * 24 * 60 * 60 * 1000);
            return "" + diffWeeks + "w";
        }

    }
}
