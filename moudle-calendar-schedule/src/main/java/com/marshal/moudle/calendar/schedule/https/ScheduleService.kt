package com.marshal.https

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleService {

    @POST("/user/login")
    @Headers("Content-Type:application/json")
    fun postScheduleLogin(@Body data: RequestBody): Call<String>

    //获取每天日程列表
    @GET("/schedule/daily/list")
    @Headers("Content-Type:application/json")
    fun getScheduleDailyList(@Query("theDay") theDay:String): Call<String>


}