package com.marshal

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter

class MApplication:MultiDexApplication() {

    companion object{
        private lateinit var mApplication:MApplication

        fun getInstance():MApplication{
            return mApplication
        }

    }

    init {
        mApplication = this
    }

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
    }

}