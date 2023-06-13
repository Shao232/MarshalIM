package com.marshal.mine

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.MINE_SETTING_PAGE
import com.marshal.R
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivitySettingBinding
import com.marshal.utils.AppUtils

@Route(path = MINE_SETTING_PAGE)
class SettingActivity : BaseViewActivity<ActivitySettingBinding>() {

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        if(hasIncludeToolbar){
            setTitle("设置页")
        }


        Log.d("TAG","versionCode:${AppUtils.getVersionCode(context?:return)}")
        Log.d("TAG","versionName:${AppUtils.getVersionName(context?:return)}")

        val appName = resources?.getString(R.string.app_name)
        binding?.tvLogoName?.text = "$appName  ${AppUtils.getVersionName(context?:return)}"

    }
}