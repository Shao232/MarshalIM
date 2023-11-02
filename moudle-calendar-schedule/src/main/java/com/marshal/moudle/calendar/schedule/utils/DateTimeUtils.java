package com.marshal.moudle.calendar.schedule.utils;

public class DateTimeUtils {

    /**
     * 通过传入指定分钟数，获取对应的毫秒
     * @param minutes  分钟  比如 5分钟，10分钟
     * @return  返回毫秒
     */
    public static long getMillis(int minutes) {
        return (long) minutes * 60 * 1000;
    }

}
