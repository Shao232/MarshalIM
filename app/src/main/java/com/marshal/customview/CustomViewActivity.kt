package com.marshal.customview

import android.view.View
import android.view.animation.AnimationUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath
import com.marshal.R
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityCustomViewBinding


@Route(path = AppRouterPath.APP_CUSTOM_VIEW_PAGE)
class CustomViewActivity : BaseViewActivity<ActivityCustomViewBinding>() {

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityCustomViewBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if (hasIncludeToolbar) {
            setTitle("自定义View")
        }

        binding?.btnStartAnim?.setOnClickListener {
            val animationUtils = AnimationUtils.loadAnimation(this, R.anim.scale_01)
            binding?.tvAnimView?.startAnimation(animationUtils)

        }



    }
}