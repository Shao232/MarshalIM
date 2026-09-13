package com.marshal.coroutines

import PlayListDialog2
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.HOME_COROUTINES
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 协程 一个线程框架
 * 非阻塞式挂起 （不卡线程）
 *
 * suspend 函数
 * 原则 耗时操作 io，耗时操作数据库，耗时操作文件
 *
 */
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

        val playListDialog = PlayListDialog2(this)
        playListDialog.show()

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
            }
        }
        //GlobalScope.launch(Dispatchers.Main) {//开始协程：主线程
            //val result = userApi.getUserSuspend("suming")//网络请求（IO 线程）
            //tv_name.text = result?.name //更新 UI（主线程）
        //}



    }
}