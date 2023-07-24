package com.marshal.mine

import AppUtils
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.huawei.hms.aaid.HmsInstanceId
import com.marshal.AppRouterPath.MINE_SETTING_PAGE
import com.marshal.R
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivitySettingBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        if (hasIncludeToolbar) {
            setTitle("设置页")
        }

        val appName = resources?.getString(R.string.app_name)
        binding?.tvLogoName?.text = "$appName  ${AppUtils.getVersionName(context ?: return)}"

        //极光 注册的id ===13065ffa4f15988d721
        val registrationId = JPushInterface.getRegistrationID(this)
        Log.d("TAG", "极光 注册的id ===${registrationId}")
        binding?.tvShowRegistrationId?.text = registrationId

        GlobalScope.launch() {

            val token = HmsInstanceId.getInstance(this@SettingActivity).getToken("108782249", "HCM")
            Log.d("TAG","hms token = ${token}")
        }

    }
}