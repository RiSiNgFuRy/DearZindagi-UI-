package com.example.dz_v30.Resources;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    private String dateFormat;

    public DateHelper(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean valid(String dateFrom, String dateTo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
        Date d1 = sdf.parse(dateFrom);
        Date d2 = sdf.parse(dateTo);
        if(d1.compareTo(d2)>0)
            return false;
        return true;
    }

    public ArrayList<Date> datesInRange(String dateFrom, String dateTo, ArrayList<String> dayOfWeek, Integer hours, Integer minutes) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
        ArrayList<Date> dates = new ArrayList<>();
        Calendar d2 = getCalendarWithoutTime(sdf.parse(dateTo),hours,minutes);
        for(String dow : dayOfWeek) {
            Calendar d1 = getCalendarWithoutTime(sdf.parse(dateFrom),hours,minutes);
            while (!d1.after(d2)) {
                if (d1.get(Calendar.DAY_OF_WEEK) == Integer.parseInt(dow)) {
                    Date d= d1.getTime();
                    dates.add(d);
                }
                d1.add(Calendar.DAY_OF_MONTH,1);
            }
        }
        for(Date c:dates)
            Log.d("Date", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c.getTime()));
        return dates;
    }

    private static Calendar getCalendarWithoutTime(Date date, Integer hours, Integer minutes) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
