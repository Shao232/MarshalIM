package com.marshal.moudle.calendar.schedule.https;

import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean
import com.marshal.moudle.calendar.schedule.pojo.PlanBean
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean
import com.marshal.moudle.calendar.schedule.pojo.UserInfoBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleService {

    //登录
    @POST("user/login")
    fun postScheduleLogin(@Body data: RequestBody): Observable<ResponseResultBean<UserInfoBean>>

    //获取每天日程列表
    @GET("schedule/daily/list")
    fun getScheduleDailyList(@Query("theDay") theDay:String): Observable<ResponseResultBean<ArrayList<DayScheduleBean>>>

    //获取规划列表
    @GET("plan/list")
    fun getPlanList(): Observable<ResponseResultBean<ArrayList<PlanBean>>>

    @POST("schedule/add")
    fun postScheduleAdd(@Body data: RequestBody):Observable<ResponseResultBean<Boolean>>


}