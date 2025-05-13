package com.marshal.android.https

import android.util.Log
import com.marshal.android.pojo.BannerBean

object WanAndroidApi {

    private val retrofit = WanAndroidHttpRequestFactory.getWanAndroidRequest()
    private val service = retrofit?.create(WanAndroidService::class.java)

    suspend fun fetchBanners(): MutableList<BannerBean>? {
        return try {
            val response = service?.getBanner()
            if (response?.isSuccessful == true) {
                Log.d("WanAndroidApi", "fetchBanners: ${response.body()}")
                response.body()?.data
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("WanAndroidApi", "error: ${e.message}")
            null
        }
    }





}