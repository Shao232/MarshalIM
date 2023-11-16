package com.marshal.moudle.calendar.schedule.https;

import com.marshal.base_common.utils.GsonUtils;
import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean;
import com.marshal.moudle.calendar.schedule.pojo.EmptyBean;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean;
import com.marshal.moudle.calendar.schedule.pojo.UserInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ScheduleJoinApi {

    private static final ScheduleService httpsService =
            Objects.requireNonNull(ScheduleHttpRequestFactory.INSTANCE.getScheduleRequest()).create(ScheduleService.class);

    public static Observable<ResponseResultBean<UserInfoBean>> postLogin(String loginAccount, String loginPassword) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "仲维昌");
        params.put("mobile", loginAccount);
        params.put("password", loginPassword);
        String json = GsonUtils.INSTANCE.objToJson(params);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        if (httpsService != null) {
            return httpsService.postScheduleLogin(body);
        } else {
            return null;
        }
    }

    public static Observable<ResponseResultBean<ArrayList<DayScheduleBean>>> getScheduleDailyList(String theDay) {
        if (httpsService != null) {
            return httpsService.getScheduleDailyList(theDay);
        } else {
            return null;
        }
    }

    public static  Observable<ResponseResultBean<ArrayList<PlanBean>>> getPlanList(){
        if (httpsService != null) {
            return httpsService.getPlanList();
        } else {
            return null;
        }
    }

    public static Observable<ResponseResultBean<Boolean>> postScheduleAdd(HashMap<String, Object> params){

        String json = GsonUtils.INSTANCE.objToJson(params);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        if (httpsService != null) {
            return httpsService.postScheduleAdd(body);
        } else {
            return null;
        }
    }

}
