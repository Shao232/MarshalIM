package com.marshal

import StoreManager
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.STORE_DATA_PAGE
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityStoreDataBinding

@Route(path = STORE_DATA_PAGE)
class StoreDataActivity : BaseViewActivity<ActivityStoreDataBinding>() {

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityStoreDataBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if (hasIncludeToolbar) {
            setTitle("数据保存")
        }

        initThread()

    }

    private fun initThread() {
        Log.d("TAG","数据保存的根目录：${StoreManager.getRootDir()}")

    }




}