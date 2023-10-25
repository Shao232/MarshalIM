package com.marshal.moudle.calendar.schedule.https;

import com.marshal.base_common.utils.GsonUtils;
import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean;
import com.marshal.moudle.calendar.schedule.pojo.UserInfoBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ScheduleJoinApi {

    public static Observable<ResponseResultBean<UserInfoBean>> postLogin(String loginAccount, String loginPassword) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "仲维昌");
        params.put("mobile", loginAccount);
        params.put("password", loginPassword);
        String json = GsonUtils.INSTANCE.objToJson(params);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        if (ScheduleHttpRequestFactory.INSTANCE.getScheduleRequest() != null) {
            return ScheduleHttpRequestFactory.INSTANCE.getScheduleRequest().create(ScheduleService.class).postScheduleLogin(body);
        } else {
            return null;
        }
    }

    public static Observable<ResponseResultBean<ArrayList<DayScheduleBean>>> getScheduleDailyList(String theDay) {
        if (ScheduleHttpRequestFactory.INSTANCE.getScheduleRequest() != null) {
            return ScheduleHttpRequestFactory.INSTANCE.getScheduleRequest().create(ScheduleService.class).getScheduleDailyList(theDay);
        } else {
            return null;
        }
    }

    public static  Observable<ResponseResultBean<ArrayList<PlanBean>>> getPlanList(){
        if (ScheduleHttpRequestFactory.INSTANCE.getScheduleRequest() != null) {
            return ScheduleHttpRequestFactory.INSTANCE.getScheduleRequest().create(ScheduleService.class).getPlanList();
        } else {
            return null;
        }
    }

}
