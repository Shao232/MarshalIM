package com.marshal.login

import android.util.Log
import com.marshal.base_common.baseview.BaseViewModel
import com.marshal.base_common.https.HttpSubscribe
import com.marshal.moudle.calendar.schedule.https.ScheduleJoinApi
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean
import com.marshal.moudle.calendar.schedule.pojo.UserInfoBean
import com.marshal.moudle.calendar.schedule.putAppInfoToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(): BaseViewModel() {


    fun login(loginAccount:String,loginPassword:String){
        ScheduleJoinApi.postLogin(loginAccount, loginPassword)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object: HttpSubscribe<ResponseResultBean<UserInfoBean>>(){
                override fun onSuccess(response: ResponseResultBean<UserInfoBean>?) {
                    val infoBean:UserInfoBean? = response?.data
                    putAppInfoToken(infoBean?.token?:"")
                    responseResult.postValue(true)
                }

                override fun onThrowable(e: Throwable?) {
                    Log.e("TAG", "loginViewModel e:${e.toString()}")
                    responseResult.postValue(false)
                }
            })
    }


}