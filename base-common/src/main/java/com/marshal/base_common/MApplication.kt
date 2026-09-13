package com.marshal.base_common

import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import cn.jiguang.api.utils.JCollectionAuth
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.bugly.crashreport.CrashReport
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

        // 必须主线程且要快：MMKV 先初始化（首屏可能用到）
        MMKV.initialize(this)

        // 必须主线程但可延后：ARouter（首屏不用路由跳转）
        if (isDebugARouter) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        // 移出主线程：以下 SDK 不影响首屏渲染
        Thread {
            // 极光推送 — 消息延迟几秒收到完全可接受
            JPushInterface.setDebugMode(isDebugARouter)
            JCollectionAuth.setAuth(this@MApplication, true)
            JPushInterface.init(this@MApplication)

            // Bugly 崩溃上报 — 启动后几秒上报不影响体验
            CrashReport.initCrashReport(this@MApplication, "ed4bbbd59c", true)
        }.start()
    }

}