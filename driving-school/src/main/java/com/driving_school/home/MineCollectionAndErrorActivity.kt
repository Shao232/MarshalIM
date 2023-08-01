package com.driving_school.home

import android.view.View
import com.driving_school.databinding.ActivityMineCollectionAndErrorBinding
import com.marshal.base_common.baseview.BaseViewActivity

class MineCollectionAndErrorActivity : BaseViewActivity<ActivityMineCollectionAndErrorBinding>() {



    override fun getResLayoutBinding(): View? {
        binding = ActivityMineCollectionAndErrorBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

    }
}