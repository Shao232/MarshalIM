package com.marshal.android

import android.view.View
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.module.wan.android.R
import com.marshal.module.wan.android.databinding.FragmentWanMineBinding

class WanMineFragment: BaseViewFragment<FragmentWanMineBinding>() {
    override fun getResLayoutId(): Int? =R.layout.fragment_wan_mine

    override fun getResLayoutBinding(): View?{
        binding = FragmentWanMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun hasToolbar(): Boolean = true

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("个人资料")
        }
    }


}