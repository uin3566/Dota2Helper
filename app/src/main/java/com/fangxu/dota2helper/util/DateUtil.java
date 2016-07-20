package com.fangxu.dota2helper.util;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DateUtil {
    private long mTodayDivider;
    private long mSevenDayDivider;

    public DateUtil() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mTodayDivider = calendar.getTimeInMillis();
        mSevenDayDivider = mTodayDivider - 6 * 24 * 60 * 60 * 1000;
    }

    public boolean isToday(long timeMillis) {
        return timeMillis >= mTodayDivider;
    }

    public boolean isInSevenDays(long timeMillis) {
        return timeMillis >= mSevenDayDivider && timeMillis < mTodayDivider;
    }

    public boolean isOutSevenDays(long timeMillis) {
        return timeMillis < mSevenDayDivider;
    }

    public String getTimeText(long timeMillis) {
        String str = null;
        if (isToday(timeMillis)) {
            str = "今天";
        }
        if (isInSevenDays(timeMillis)) {
            str = "七天内";
        }
        if (isOutSevenDays(timeMillis)) {
            str = "更早";
        }
        return str;
    }
}
