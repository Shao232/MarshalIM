package com.marshal.android.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.marshal.android.common.WanAndroidViewModel
import com.marshal.android.https.WanAndroidApi
import com.marshal.android.pojo.BannerBean
import com.marshal.android.pojo.WenDaBean
import kotlinx.coroutines.launch

class WanHomeViewModel:WanAndroidViewModel() {

    val bannersMutableLiveData: MutableLiveData<MutableList<BannerBean>?> = MutableLiveData()
    val wendaMutableLiveData:MutableLiveData<MutableList<WenDaBean>?> = MutableLiveData()

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

    /**
     * 获取问答列表
     */
    fun loadWenDa(){
        viewModelScope.launch {
            val resultListData =  WanAndroidApi.fetchWenda()
            if(resultListData?.isNotEmpty() == true){
                wendaMutableLiveData.postValue(resultListData)
            }else{
                wendaMutableLiveData.postValue(null)
            }
        }
    }

}