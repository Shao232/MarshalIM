package com.marshal.android.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.marshal.android.common.WanAndroidViewModel
import com.marshal.android.https.WanAndroidApi
import com.marshal.android.pojo.BannerBean
import kotlinx.coroutines.launch

class WanHomeViewModel:WanAndroidViewModel() {

    val bannersMutableLiveData: MutableLiveData<MutableList<BannerBean>?> = MutableLiveData()

    fun loadBanners(){
       viewModelScope.launch {
          val resultListData =  WanAndroidApi.fetchBanners()
           if(resultListData?.isNotEmpty() == true){
               bannersMutableLiveData.postValue(resultListData)
           }else{
               bannersMutableLiveData.postValue(null)
           }
       }
    }

}