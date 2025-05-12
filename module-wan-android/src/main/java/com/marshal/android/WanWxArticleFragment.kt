package com.marshal.android

import android.view.View
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.module.wan.android.R
import com.marshal.module.wan.android.databinding.FragmentWanWxArticleBinding

class WanWxArticleFragment:BaseViewFragment<FragmentWanWxArticleBinding>(){
    override fun getResLayoutId(): Int? = R.layout.fragment_wan_wx_article

    override fun getResLayoutBinding(): View? {
        binding = FragmentWanWxArticleBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun hasToolbar(): Boolean = true

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("公众号")
        }
    }
}