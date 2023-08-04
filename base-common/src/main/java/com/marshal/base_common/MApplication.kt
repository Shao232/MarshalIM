package com.marshal.base_common

import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import cn.jiguang.api.utils.JCollectionAuth
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.tencent.mmkv.MMKV

class MApplication : MultiDexApplication() {

    //ARouter debug开关：true-open;false-close
    private val isDebugARouter = BuildConfig.DEBUG

    companion object {
        private lateinit var mApplication: MApplication
        fun getInstance(): MApplication {
            return mApplication
        }
    }

    init {
        mApplication = this
    }

    override fun onCreate() {
        super.onCreate()

        JPushInterface.setDebugMode(isDebugARouter)
        // 调整点一：初始化代码前增加setAuth调用
        // var isPrivacyReady = false // app根据是否已弹窗获取隐私授权来赋值
        // if (!isPrivacyReady) {
        //     JCollectionAuth.setAuth(this, true) // 后续初始化过程将被拦截
        // }
        JCollectionAuth.setAuth(this, true)
        JPushInterface.init(this)

        if (isDebugARouter) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        MMKV.initialize(this)

        val options =  EMOptions()
        //环信的appkey
        options.appKey = "150136#marshalim"
        options.autoLogin = true
        EMClient.getInstance().init(this, options)

    }

}