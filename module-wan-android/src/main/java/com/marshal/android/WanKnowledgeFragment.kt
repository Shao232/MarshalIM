package com.marshal.android

import android.view.View
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.module.wan.android.R
import com.marshal.module.wan.android.databinding.FragmentWanKnowledgeBinding

class WanKnowledgeFragment:  BaseViewFragment<FragmentWanKnowledgeBinding>() {
    override fun getResLayoutId(): Int? = R.layout.fragment_wan_knowledge

    override fun getResLayoutBinding(): View? {
        binding = FragmentWanKnowledgeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun hasToolbar(): Boolean =true

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("知识体系")
        }



    }
}