package com.marshal.moudle.calendar.schedule.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.marshal.base_common.utils.GsonUtils;

import kotlinx.parcelize.Parcelize;

@Parcelize
public class CalendarScheduleBean implements Parcelable {

    private int year;
    private int startMonth;
    private int endMonth;
    private int startDay;
    private int endDay;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;
    private int weekInfo;
    private boolean clickDayScheduleStatus = false;
    private boolean clickWeekScheduleStatus = false;

    public CalendarScheduleBean(int year, int startMonth,int endMonth, int startDay,int endDay,int startHour,int endHour,int startMinute,int endMinute, int weekInfo) {
        this.year = year;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
        this.weekInfo = weekInfo;
    }

    protected CalendarScheduleBean(Parcel in) {
        year = in.readInt();
        startMonth = in.readInt();
        startDay = in.readInt();
        endMonth = in.readInt();
        endDay = in.readInt();
        weekInfo = in.readInt();
        startHour = in.readInt();
        endHour = in.readInt();
        startMinute = in.readInt();
        endMinute = in.readInt();
        clickDayScheduleStatus = in.readInt() != 0;
        clickWeekScheduleStatus = in.readInt() != 0;
    }

    public static final Creator<CalendarScheduleBean> CREATOR = new Creator<CalendarScheduleBean>() {
        @Override
        public CalendarScheduleBean createFromParcel(Parcel in) {
            return new CalendarScheduleBean(in);
        }

        @Override
        public CalendarScheduleBean[] newArray(int size) {
            return new CalendarScheduleBean[size];
        }
    };

    public CalendarScheduleBean setYear(int year){
        this.year = year;
        return this;
    }

    public CalendarScheduleBean setStartMonth(int startMonth){
        this.startMonth = startMonth;
        return this;
    }

    public CalendarScheduleBean setEndMonth(int endMonth) {
        this.endMonth = endMonth;
        return this;
    }

    public CalendarScheduleBean setStartDay(int startDay){
        this.startDay = startDay;
        return this;
    }

    public CalendarScheduleBean setEndDay(int endDay) {
        this.endDay = endDay;
        return this;
    }

    public CalendarScheduleBean setStartHour(int startHour){
        this.startHour = startHour;
        return this;
    }

    public CalendarScheduleBean setEndHour(int endHour) {
        this.endHour = endHour;
        return this;
    }

    public CalendarScheduleBean setStartMinute(int startMinute) {
        this.startMinute = startMinute;
        return this;
    }

    public CalendarScheduleBean setEndMinute(int endMinute) {
        this.endMinute = endMinute;
        return this;
    }

    public CalendarScheduleBean setClickDayScheduleStatus(boolean clickDayScheduleStatus) {
        this.clickDayScheduleStatus = clickDayScheduleStatus;
        return this;
    }

    public CalendarScheduleBean setClickWeekScheduleStatus(boolean clickWeekScheduleStatus) {
        this.clickWeekScheduleStatus = clickWeekScheduleStatus;
        return this;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public int getYear() {
        return year;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(int weekInfo) {
        this.weekInfo = weekInfo;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public boolean isClickDayScheduleStatus() {
        return clickDayScheduleStatus;
    }

    public boolean isClickWeekScheduleStatus() {
        return clickWeekScheduleStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return GsonUtils.INSTANCE.objToJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(startMonth);
        dest.writeInt(startDay);
        dest.writeInt(endMonth);
        dest.writeInt(endDay);
        dest.writeInt(weekInfo);
        dest.writeInt(startHour);
        dest.writeInt(endHour);
        dest.writeInt(startMinute);
        dest.writeInt(endMinute);
        dest.writeInt(clickDayScheduleStatus?1:0);
        dest.writeInt(clickWeekScheduleStatus?1:0);
    }


}
