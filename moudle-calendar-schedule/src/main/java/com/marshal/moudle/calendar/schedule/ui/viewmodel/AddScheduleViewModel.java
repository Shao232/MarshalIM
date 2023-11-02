package com.marshal.moudle.calendar.schedule.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.marshal.base_common.baseview.BaseViewModel;
import com.marshal.base_common.utils.GsonUtils;
import com.marshal.moudle.calendar.schedule.StoreCalendarDataKt;
import com.marshal.moudle.calendar.schedule.https.HttpSubscribe;
import com.marshal.moudle.calendar.schedule.https.ScheduleJoinApi;
import com.marshal.moudle.calendar.schedule.pojo.EmptyBean;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddScheduleViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> responseResult = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> responseAddSchedule = new MutableLiveData<Boolean>();
    public MutableLiveData<ArrayList<PlanBean>> resultListData = new MutableLiveData<>();

    public void getPlanList(){
        ScheduleJoinApi.getPlanList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscribe<>() {
                    @Override
                    public void onSuccess(ResponseResultBean<ArrayList<PlanBean>> response) {
                        Log.d("TAG","AddScheduleViewModel data:"+response.getData());
                        responseResult.postValue(true);
                        resultListData.postValue(response.getData());
                    }

                    @Override
                    public void onThrowable(Throwable e) {
                        responseResult.postValue(false);
                        Log.e("TAG", "AddScheduleViewModel e:"+e.toString());
                    }
                });
    }

    public void postScheduleAdd(String title,String remark,Long startTime,Long endTime,ArrayList<Long> aHeadList,String planId,String planTitle){
        HashMap<String,Object> params = new HashMap<>();
        params.put("title",title);
        params.put("remark",remark);
        params.put("startTime",String.valueOf(startTime));
        params.put("endTime",String.valueOf(endTime));

        JsonArray jsonArray = new JsonArray();
        for(Long number:aHeadList){
            jsonArray.add(number);
        }
        params.put("aheadTimeList",jsonArray);

        if(!planId.isEmpty() && !planTitle.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id",planId);
            jsonObject.addProperty("title",planTitle);
            params.put("plan",jsonObject);
        }

        ScheduleJoinApi.postScheduleAdd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscribe<>() {
                    @Override
                    public void onSuccess(ResponseResultBean<Boolean> response) {
                        responseAddSchedule.postValue(true);
                        Log.d("TAG","AddScheduleViewModel data:"+response.getData());
                    }

                    @Override
                    public void onThrowable(Throwable e) {
                        responseAddSchedule.postValue(false);
                        Log.e("TAG", "AddScheduleViewModel e:"+e.toString());
                    }
                });
    }


}
