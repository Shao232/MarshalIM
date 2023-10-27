package com.marshal.moudle.calendar.schedule.pojo;

import androidx.annotation.NonNull;

import com.marshal.base_common.utils.GsonUtils;

public class CalendarScheduleViewBean {

    private int startHour;
    private int endHour;
    private String title;

    public CalendarScheduleViewBean(){}

    public CalendarScheduleViewBean(int startHour, int endHour, String title) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.title = title;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return GsonUtils.INSTANCE.objToJson(this);
    }
}
