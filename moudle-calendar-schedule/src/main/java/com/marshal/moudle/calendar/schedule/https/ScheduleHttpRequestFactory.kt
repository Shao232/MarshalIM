package com.marshal.moudle.calendar.schedule.https
import com.marshal.base_common.https.CustomHttpLogger
import com.marshal.moudle.calendar.schedule.getAppInfoToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ScheduleHttpRequestFactory {

    private val interceptor = HttpLoggingInterceptor(CustomHttpLogger.custom)
    private var retrofit: Retrofit? = null

    //https://mouce.xyz
    private const val scheduleUrl: String = "https://mouce.xyz/api/"
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
                requestHeaderBuilder.addHeader("Content-Type","application/json")
                val requestHeader = requestHeaderBuilder.build()
                chain.proceed(requestHeader)
            })
            .addInterceptor(interceptor)
            .writeTimeout(3000, TimeUnit.MILLISECONDS)
            .readTimeout(3000, TimeUnit.MILLISECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(scheduleUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }


}