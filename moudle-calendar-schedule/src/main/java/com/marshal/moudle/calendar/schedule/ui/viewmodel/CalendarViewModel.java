package com.marshal.moudle.calendar.schedule.ui.viewmodel;

import android.util.Log;

import com.marshal.base_common.baseview.BaseViewModel;
import com.marshal.moudle.calendar.schedule.https.HttpSubscribe;
import com.marshal.moudle.calendar.schedule.https.ScheduleHttpRequestFactory;
import com.marshal.moudle.calendar.schedule.https.ScheduleJoinApi;
import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean;
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CalendarViewModel extends BaseViewModel {

    public void getScheduleData(String theDay) {

        ScheduleJoinApi.getScheduleDailyList(theDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscribe<>() {
                    @Override
                    public void onSuccess(ResponseResultBean<ArrayList<DayScheduleBean>> response) {
                        Log.d("TAG", "CalendarViewModel response:" + response.getData());
                        getResponseResult().postValue(true);
                    }

                    @Override
                    public void onThrowable(Throwable e) {
                        Log.e("TAG", "CalendarViewModel e:" + e.toString());
                        getResponseResult().postValue(false);
                    }
                });

    }

}
