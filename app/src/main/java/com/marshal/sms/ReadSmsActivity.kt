package com.marshal.sms

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityReadSmsBinding
import com.marshal.pojo.ReadSmSBean
import com.marshal.sms.adapter.ReadSmSAdapter
import okhttp3.internal.filterList

@Route(path = AppRouterPath.APP_SMS_LIST_PAGE)
class ReadSmsActivity : BaseViewActivity<ActivityReadSmsBinding>() {

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityReadSmsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if (hasIncludeToolbar) {
            setTitle("短信列表")
        }

        val dataList: ArrayList<ReadSmSBean> = ArrayList()
        val smsList = SmsUtils.obtainPhoneMessage(this)
        Log.d("TAG", "短信数据 count:${smsList?.size}")
        if (smsList != null) {
            var phoneSBean: ReadSmSBean?
            for (itemMap: Map<String, String?> in smsList) {
                phoneSBean = ReadSmSBean()
                phoneSBean.phoneNumber = itemMap["number"]
                phoneSBean.smsContent = itemMap["content"]
                dataList.add(phoneSBean)
            }

            val expressDataList = dataList.filterList {
                this.smsContent?.contains(ExpressUtils.TU_XI_SHENG_HUO) == true
                        || this.smsContent?.contains(ExpressUtils.FENG_CHAO) == true
                        || this.smsContent?.contains(ExpressUtils.JI_TU_SU_DI) == true
                        || this.smsContent?.contains(ExpressUtils.YUN_DA_KUAI_DI) == true
                        || this.smsContent?.contains(ExpressUtils.KEY_1) == true
                        || this.smsContent?.contains(ExpressUtils.KEY_2) == true
            }

            val adapter = ReadSmSAdapter()
            (expressDataList as? ArrayList<ReadSmSBean>)?.let { adapter.addListAll(it) }
            binding?.recyclerView?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            binding?.recyclerView?.adapter = adapter


        }
    }


}
