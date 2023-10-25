package com.marshal.https

import com.marshal.moudle.calendar.schedule.https.CustomHttpLogger
import getAppInfoToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object HttpRequestFactory {

    private val interceptor = HttpLoggingInterceptor(CustomHttpLogger.custom)
    private var retrofit: Retrofit? = null

    private const val scheduleUrl: String = "http://114.116.15.34:80"
    private var okHttpClient: OkHttpClient? = null

    init {

    }



    fun getScheduleRequest(): Retrofit? {
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val requestHeaderBuilder = request.newBuilder()
                //登录后添加token
                val appToken = getAppInfoToken()
                if (appToken?.isNotEmpty() == true) {
                    requestHeaderBuilder.addHeader("Token", appToken)
                }
                val requestHeader = requestHeaderBuilder.build()
                chain.proceed(requestHeader)
            })
            .addInterceptor(interceptor)
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(scheduleUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }


}