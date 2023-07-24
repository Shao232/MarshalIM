package com.marshal.mine

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.ABOUT_APP_PAGE
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityAboutAppBinding

@Route(path = ABOUT_APP_PAGE)
class AboutAppActivity : BaseViewActivity<ActivityAboutAppBinding>() {

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if(hasIncludeToolbar) {
            setTitle("关于版本")
        }

    }
}