package com.marshal.android

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.android.WanAndroidRouterPath.WAN_ANDROID_Home
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.module.wan.android.databinding.ActivityWanAndroidBinding

@Route(path = WAN_ANDROID_Home)
class WanAndroidActivity : BaseViewActivity<ActivityWanAndroidBinding>() {

    override fun getResLayoutBinding(): View? {
        binding = ActivityWanAndroidBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

    }
}