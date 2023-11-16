package com.marshal.moudle.calendar.schedule.utils;

public enum DayAndMonthUtils {
    REPETITION(0),
    ONLY_ONE(1);

    private final int dayType;

    DayAndMonthUtils(int type) {
        this.dayType = type;
    }

    public int getDayType() {
        return dayType;
    }
}
