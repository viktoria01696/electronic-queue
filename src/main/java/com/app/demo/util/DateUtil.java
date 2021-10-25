package com.app.demo.util;

import com.app.demo.exception.UnavailableDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat formatForDate =
            new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat formatForDateAndTime =
            new SimpleDateFormat("yyyy-MM-dd k:m:s:S");

    public static Calendar removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar removeSeconds(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar createDate(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Date parseDate(String date) {
        Date desiredDate;
        try {
            desiredDate = formatForDate.parse(date);
        } catch (ParseException e) {
            throw new UnavailableDateException();
        }
        return desiredDate;
    }

    public static Date parseDate(String date, String time) {
        Date desiredDate;
        try {
            desiredDate = formatForDateAndTime.parse(date + " " + time);
        } catch (ParseException e) {
            throw new UnavailableDateException();
        }
        return desiredDate;
    }

    public static Date getDatePlusDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

}
