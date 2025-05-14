package com.marshal.android.webview

import android.os.Build
import android.text.Html
import android.view.View
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.module.wan.android.databinding.ActivityWanAndroidWebViewBinding


class WanAndroidWebViewActivity : BaseViewActivity<ActivityWanAndroidWebViewBinding>() {
    override fun getResLayoutBinding(): View? {
       binding = ActivityWanAndroidWebViewBinding.inflate(layoutInflater)
        return binding?.root
    }

    private var data:String? = ""

    override fun initView() {

        if(intent.getStringExtra("web_data_key")?.isNotEmpty() == true) {
            data = intent.getStringExtra("web_data_key")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding?.tvShowHtmlContent?.text = Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT)
        }else {
            binding?.tvShowHtmlContent?.text = Html.fromHtml(data)
        }
    }

}