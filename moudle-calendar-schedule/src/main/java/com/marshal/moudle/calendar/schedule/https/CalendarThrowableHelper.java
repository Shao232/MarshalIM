package com.marshal.moudle.calendar.schedule.https;

import android.util.Log;

import java.net.SocketTimeoutException;

public class CalendarThrowableHelper {

    /**
     * 常规捕获异常
     * @param e 异常信息
     * @return  true 捕获成功 拦截，false 下发异常
     */
    public static String tryThrowableMessage(Throwable e){
      if(e instanceof SocketTimeoutException) {
          Log.d("TAG","e :"+e);
          return "网络异常，请检查网络连接";
      }

        return e.getMessage();
    }

}
