package com.marshal.https

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object HttpRequestFactory {

    private val interceptor = HttpLoggingInterceptor(CustomHttpLogger.custom)
    private var retrofit: Retrofit? = null
    private const val iMarshalUrl: String = "https://www.marshalim.club"
    private var okHttpClient: OkHttpClient? = null

    init {

    }

    fun getMarshalIMRequest() {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient ?: return)
            .baseUrl(iMarshalUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }




}