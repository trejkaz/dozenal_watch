package org.trypticon.dozenalwatchface;

import com.ustwo.clockwise.WatchFaceTime;

/**
 * Encapsulates dozenal time calculation.
 */
class DozenalTime {
    private static final int[] YEAR_OFFSETS = {
            42, // never used
            -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final int[] DAY_OFFSETS = {
            42, // never used
            306, 337,   0,  31,  61,  92,
            122, 153, 184, 214, 245, 275,
            };
    private static final int DAYS_PER_MONTH = 30;
    private static final int DAYS_PER_WEEK = 6;

    private static final int GREGORIAN_MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

    private static final int GREGORIAN_MILLIS_PER_DOZENAL_HOUR = GREGORIAN_MILLIS_PER_DAY / 12;
    private static final int GREGORIAN_MILLIS_PER_DOZENAL_MINUTE = GREGORIAN_MILLIS_PER_DOZENAL_HOUR / 12;
    private static final int GREGORIAN_MILLIS_PER_DOZENAL_SECOND = GREGORIAN_MILLIS_PER_DOZENAL_MINUTE / 12;
    private static final float GREGORIAN_MILLIS_PER_DOZENAL_THIRD = (float) GREGORIAN_MILLIS_PER_DOZENAL_SECOND / 12;

    private static final int HOURS_PER_DAY = 12;
    private static final int MINUTES_PER_HOUR = 12;
    private static final int SECONDS_PER_MINUTE = 12;
    private static final int THIRDS_PER_SECOND = 12;

    private GregorianTime time = new GregorianTime();

    private int year;
    private int month;
    private int dayOfMonth;
    private int dayOfWeek;
    private int hourOfDay;
    private int minuteOfHour;
    private int secondOfMinute;
    private int thirdOfSecond;
    private float hourTurns;
    private float minuteTurns;
    private float secondTurns;
    private float thirdTurns;

    public int getYear() {
        return year;
    }

    //1..13
    public int getMonth() {
        return month;
    }

    //1..30
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    //0..5
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    //0..11
    int getHourOfDay() {
        return hourOfDay;
    }

    //0..11
    int getMinuteOfHour() {
        return minuteOfHour;
    }

    //0..11
    int getSecondOfMinute() {
        return secondOfMinute;
    }

    //0..11
    int getThirdOfSecond() {
        return thirdOfSecond;
    }

    float getHourTurns() {
        return hourTurns;
    }

    float getMinuteTurns() {
        return minuteTurns;
    }

    float getSecondTurns() {
        return secondTurns;
    }

    float getThirdTurns() {
        return thirdTurns;
    }

    void setTo(WatchFaceTime time) {
        this.time.setTo(time);
        recompute();
    }

    void setTo(GregorianTime time) {
        this.time.setTo(time);
        recompute();
    }

    private void recompute() {
        int gregorianYear = time.year;
        int gregorianMonthOfYear = time.month;
        int gregorianDayOfMonth = time.dayOfMonth;

        year = gregorianYear + YEAR_OFFSETS[gregorianMonthOfYear];
        int dayOfYear = DAY_OFFSETS[gregorianMonthOfYear] + gregorianDayOfMonth - 1;
        month = (dayOfYear / DAYS_PER_MONTH) + 1;
        dayOfMonth = (dayOfYear % DAYS_PER_MONTH) + 1;
        dayOfWeek = dayOfYear % DAYS_PER_WEEK;

        // We do recompute this from the calendar, because sometimes an hour in the day
        // is missing. So this gives us the effect of getting free daylight savings support.
        int dayMillis = time.computeDayMillis();

        hourOfDay = dayMillis / GREGORIAN_MILLIS_PER_DOZENAL_HOUR;
        int tmp = dayMillis % GREGORIAN_MILLIS_PER_DOZENAL_HOUR;
        minuteOfHour = tmp / GREGORIAN_MILLIS_PER_DOZENAL_MINUTE;
        tmp %= GREGORIAN_MILLIS_PER_DOZENAL_MINUTE;
        secondOfMinute = tmp / GREGORIAN_MILLIS_PER_DOZENAL_SECOND;
        tmp %= GREGORIAN_MILLIS_PER_DOZENAL_SECOND;
        thirdOfSecond = (int) (tmp / GREGORIAN_MILLIS_PER_DOZENAL_THIRD);

        hourTurns = dayMillis / (float) GREGORIAN_MILLIS_PER_DAY;
        minuteTurns = hourTurns * DozenalTime.MINUTES_PER_HOUR;
        secondTurns = minuteTurns * DozenalTime.SECONDS_PER_MINUTE;
        thirdTurns = secondTurns * DozenalTime.THIRDS_PER_SECOND;
    }
}
