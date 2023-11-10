package com.marshal.moudle.calendar.schedule.pojo;

import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.marshal.base_common.utils.GsonUtils;

import java.util.Comparator;

public class CalendarScheduleRectFBean implements Comparable<CalendarScheduleRectFBean> {

    private long id;
    private int tableCount;
    private RectF rectDrawSchedule;
    private String title;

    public CalendarScheduleRectFBean(){}

    public CalendarScheduleRectFBean(long id,int tableCount, RectF rectDrawSchedule, String title) {
        this.id = id;
        this.tableCount = tableCount;
        this.rectDrawSchedule = rectDrawSchedule;
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public RectF getRectDrawSchedule() {
        return rectDrawSchedule;
    }

    public void setRectDrawSchedule(RectF rectDrawSchedule) {
        this.rectDrawSchedule = rectDrawSchedule;
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

    @Override
    public int compareTo(CalendarScheduleRectFBean o) {
        return Integer.compare(getTableCount(), o.getTableCount());
    }
}
