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
    private int month;
    private int day;
    private int startHour;
    private int endHour;
    private int weekInfo;

    public CalendarScheduleBean(int year, int month, int day,int startHour,int endHour, int weekInfo) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        this.weekInfo = weekInfo;
    }

    protected CalendarScheduleBean(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        weekInfo = in.readInt();
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(int weekInfo) {
        this.weekInfo = weekInfo;
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
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(weekInfo);
        dest.writeInt(startHour);
        dest.writeInt(endHour);
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
}
