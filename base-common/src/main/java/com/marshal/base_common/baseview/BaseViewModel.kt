package com.marshal.base_common.baseview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {

    val errorData:MutableLiveData<Throwable> = MutableLiveData()

}