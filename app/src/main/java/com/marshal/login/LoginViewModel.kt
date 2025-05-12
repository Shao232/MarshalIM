package com.marshal.login

import com.marshal.base_common.baseview.BaseViewModel

class LoginViewModel: BaseViewModel() {


    fun login(){
        responseResult.postValue(true)
//        ScheduleJoinApi.postLogin(loginAccount, loginPassword)
//            .subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(object: HttpSubscribe<ResponseResultBean<UserInfoBean>>(){
//                override fun onSuccess(response: ResponseResultBean<UserInfoBean>?) {
//                    val infoBean:UserInfoBean? = response?.data
//                    putAppInfoToken(infoBean?.token?:"")
//                    responseResult.postValue(true)
//                }
//
//                override fun onThrowable(e: Throwable?) {
//                    Log.e("TAG", "loginViewModel e:${e.toString()}")
//                    responseResult.postValue(false)
//                }
//            })
    }


}