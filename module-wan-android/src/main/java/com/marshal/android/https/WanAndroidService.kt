package com.marshal.android.https

import com.marshal.android.pojo.BannerBean
import retrofit2.Response
import retrofit2.http.GET

interface WanAndroidService {

    @GET("/banner/json")
    suspend fun getBanner(): Response<WanAndroidBaseBean<MutableList<BannerBean>>>

}