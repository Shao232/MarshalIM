package com.marshal.android.https

import com.marshal.base_common.https.CustomHttpLogger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object WanAndroidHttpRequestFactory {

    private val interceptor = HttpLoggingInterceptor(CustomHttpLogger.custom)
    private var retrofit: Retrofit? = null

    private const val baseUrl: String = "https://www.wanandroid.com"
    private var okHttpClient: OkHttpClient? = null

    fun getWanAndroidRequest(): Retrofit? {
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .writeTimeout(3000, TimeUnit.MILLISECONDS)
            .readTimeout(3000, TimeUnit.MILLISECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient?: OkHttpClient())
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }

}