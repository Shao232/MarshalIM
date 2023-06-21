package com.marshal.coroutines

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.HOME_COROUTINES
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityCoroutinesBinding

@Route(path = HOME_COROUTINES)
class CoroutinesActivity : BaseViewActivity<ActivityCoroutinesBinding>() {


    override fun hasToolbar(): Boolean = true


    override fun getResLayoutBinding(): View? {
        binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("学习协程")
        }


        //GlobalScope.launch(Dispatchers.Main) {//开始协程：主线程
            //val result = userApi.getUserSuspend("suming")//网络请求（IO 线程）
            //tv_name.text = result?.name //更新 UI（主线程）
        //}



    }
}