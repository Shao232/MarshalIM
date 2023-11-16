package com.marshal.moudle.calendar.schedule.pojo;

import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.marshal.base_common.utils.GsonUtils;

import java.util.Comparator;

public class CalendarScheduleRectFBean implements Comparable<CalendarScheduleRectFBean> {

    //当前对象的唯一标识符 id
    private long id;
    //日程占格子的总数
    private int tableCount;
    //需要绘制的日程范围RectF
    private RectF rectDrawSchedule;
    //用户写的日程标题
    private String title;
    //当前日程的顶部的时间点：比如1点，2点
    private String topHour;
    //当前日程的底部时间点，比如顶部1点，底部1点，占一个格子，如果是顶部是1点，底部是2点，占2个格子
    private String bottomHour;

    public CalendarScheduleRectFBean(){}

    public CalendarScheduleRectFBean(long id,int tableCount, RectF rectDrawSchedule, String title) {
        this.id = id;
        this.tableCount = tableCount;
        this.rectDrawSchedule = rectDrawSchedule;
        this.title = title;
    }

    public void setTopHour(String topHour) {
        this.topHour = topHour;
    }

    public String getTopHour() {
        return topHour;
    }

    public void setBottomHour(String bottomHour) {
        this.bottomHour = bottomHour;
    }

    public String getBottomHour() {
        return bottomHour;
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
