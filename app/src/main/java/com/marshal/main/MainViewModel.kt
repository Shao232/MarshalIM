package com.marshal.main

import androidx.lifecycle.MutableLiveData
import com.marshal.base_common.baseview.BaseViewModel

class MainViewModel:BaseViewModel() {

    val sendLoginInfo:MutableLiveData<Boolean> = MutableLiveData()

    fun setSendLoginSuccessInfo(loginSuccess:Boolean){
        sendLoginInfo.postValue(loginSuccess)
    }

}