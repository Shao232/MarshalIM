package com.marshal.moudle.calendar.schedule.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.marshal.base_common.baseview.BaseViewModel;
import com.marshal.base_common.https.HttpSubscribe;
import com.marshal.moudle.calendar.schedule.https.ScheduleJoinApi;
import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean;
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CalendarViewModel extends BaseViewModel {

    public MutableLiveData<ArrayList<DayScheduleBean>> responseScheduleLiveData = new MutableLiveData<>();

    public void getScheduleData(String theDay) {

        ScheduleJoinApi.getScheduleDailyList(theDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscribe<>() {
                    @Override
                    public void onSuccess(ResponseResultBean<ArrayList<DayScheduleBean>> response) {
                        Log.d("TAG", "CalendarViewModel response:" + response.getData());
                        getResponseResult().postValue(true);
                        responseScheduleLiveData.postValue(response.getData());
                    }

                    @Override
                    public void onThrowable(Throwable e) {
                        Log.e("TAG", "CalendarViewModel e:" + e.toString());

                        getResponseResult().postValue(false);
                        getErrorData().postValue(e);
                    }
                });

    }

}
