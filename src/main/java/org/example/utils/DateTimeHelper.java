package org.example.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {

    private static final Calendar calendar = Calendar.getInstance();

    public Date getDateFromEpoch(String epoch) {
        return new Date(Long.parseLong(epoch) * 1000);
    }

    public int getDayOfMonth(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonthOfYear(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
}
