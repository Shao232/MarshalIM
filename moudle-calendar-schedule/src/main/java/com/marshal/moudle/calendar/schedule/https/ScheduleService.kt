package com.marshal.moudle.calendar.schedule.https;

import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean
import com.marshal.moudle.calendar.schedule.pojo.PlanBean
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean
import com.marshal.moudle.calendar.schedule.pojo.UserInfoBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleService {

    @POST("/user/login")
    @Headers("Content-Type:application/json")
    fun postScheduleLogin(@Body data: RequestBody): Observable<ResponseResultBean<UserInfoBean>>

    //获取每天日程列表
    @GET("/schedule/daily/list")
    @Headers("Content-Type:application/json")
    fun getScheduleDailyList(@Query("theDay") theDay:String): Observable<ResponseResultBean<ArrayList<DayScheduleBean>>>

    @GET("plan/list")
    @Headers("Content-Type:application/json")
    fun getPlanList(): Observable<ResponseResultBean<ArrayList<PlanBean>>>

}