package com.marshal.moudle.calendar.schedule.pojo;

import java.io.Serializable;

public class AHeadTimeBean implements Serializable {

    private String title;
    private long aHeadMilli;

    public AHeadTimeBean() {
    }

    public AHeadTimeBean(String title, long aHeadMilli) {
        this.title = title;
        this.aHeadMilli = aHeadMilli;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAHeadMilli(long aHeadMilli) {
        this.aHeadMilli = aHeadMilli;
    }

    public String getTitle() {
        return title;
    }

    public long getAHeadMilli() {
        return aHeadMilli;
    }
}
