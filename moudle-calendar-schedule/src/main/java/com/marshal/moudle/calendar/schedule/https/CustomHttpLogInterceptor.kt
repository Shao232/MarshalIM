package com.marshal.moudle.calendar.schedule.https

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class CustomHttpLogger{

    companion object{
        @JvmField
        val custom: HttpLoggingInterceptor.Logger = DefaultLogger()
        private class DefaultLogger : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("TAG","message :${message}")
            }
        }
    }

}