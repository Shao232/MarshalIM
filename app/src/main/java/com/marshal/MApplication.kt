package com.marshal

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter

class MApplication:MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
    }

}