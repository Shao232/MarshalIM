package com.marshal.base_common.https;

import android.util.Log;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLException;

import retrofit2.HttpException;

public class CalendarThrowableHelper {

    /**
     * 常规捕获异常
     *
     * @param e 异常信息
     * @return true 捕获成功 拦截，false 下发异常
     */
    public static String tryThrowableMessage(Throwable e) {
        Log.d("TAG", "e :" + e);
        if (e instanceof SocketTimeoutException) {
            return "网络异常，请检查网络连接";
        } else if (e instanceof SocketException) {
            return "网络异常，请检查网络连接";
        } else if (e instanceof SSLException) {
            return "网络异常，请检查网络连接";
        }else if(e instanceof HttpException){
            return "网络异常，请检查网络连接";
        }

        return e.getMessage();
    }

}
