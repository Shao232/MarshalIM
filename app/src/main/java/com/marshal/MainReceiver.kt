package com.marshal

import android.content.Context
import android.util.Log
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver

class MainReceiver: JPushMessageReceiver() {


    override fun onNotifyMessageArrived(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageArrived(p0, p1)
        Log.d("TAG","接收到的通知内容 >>${p1.toString()}")
    }

    override fun onNotifyMessageOpened(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageOpened(p0, p1)
        Log.d("TAG","点击的通知内容 >>${p1.toString()}")
        //ARouter.getInstance().build(STORE_DATA_PAGE).navigation(p0)



    }

}