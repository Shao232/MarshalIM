package com.marshal.base_common

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV

class MApplication : MultiDexApplication() {

    //ARouter debug开关：true-open;false-close
    private val isDebugARouter = true

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
        if (isDebugARouter) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(this)
        MMKV.initialize(this)
    }

}