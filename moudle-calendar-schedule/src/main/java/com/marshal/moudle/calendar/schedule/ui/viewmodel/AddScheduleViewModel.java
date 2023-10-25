package com.marshal.moudle.calendar.schedule.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marshal.base_common.baseview.BaseViewModel;
import com.marshal.moudle.calendar.schedule.StoreCalendarDataKt;
import com.marshal.moudle.calendar.schedule.https.HttpSubscribe;
import com.marshal.moudle.calendar.schedule.https.ScheduleJoinApi;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddScheduleViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> responseResult = new MutableLiveData<Boolean>();

    public void getPlanList(){
        ScheduleJoinApi.getPlanList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscribe<>() {
                    @Override
                    public void onSuccess(ResponseResultBean<ArrayList<PlanBean>> response) {
                        Log.d("TAG","AddScheduleViewModel data:"+response.getData());
                        responseResult.postValue(true);
                    }

                    @Override
                    public void onThrowable(Throwable e) {
                        responseResult.postValue(false);


                        Log.e("TAG", "AddScheduleViewModel e:"+e.toString());
                    }
                });
    }


}
