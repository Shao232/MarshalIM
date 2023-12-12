package com.marshal.https

import com.marshal.base_common.https.CustomHttpLogger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object HttpRequestFactory {

    private val interceptor = HttpLoggingInterceptor(CustomHttpLogger.custom)
    private var retrofit: Retrofit? = null
    private const val iMarshalUrl: String = "https://www.marshalim.club"
    private lateinit var okHttpClient: OkHttpClient


    fun getMarshalIMRequest(): Retrofit? {
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(iMarshalUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }


}