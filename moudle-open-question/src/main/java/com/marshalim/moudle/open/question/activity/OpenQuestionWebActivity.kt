package com.marshalim.moudle.open.question.activity

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshalim.moudle.open.question.databinding.ActivityOpenQuestionWebBinding
import com.marshalim.moudle.open.question.utils.OpenQuestionRouter

@Route(path = OpenQuestionRouter.OPEN_QUESTION_WEB_BANK)
class OpenQuestionWebActivity : BaseViewActivity<ActivityOpenQuestionWebBinding>() {

    private val openURL = "http://39.98.74.62:7080/#/"

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityOpenQuestionWebBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if(hasIncludeToolbar) {
            setTitle("开大考题")
        }
        Log.d("TAG","====进入webView====")

        initWebView()

        if (openURL.isNotEmpty()) {
            binding?.webView?.loadUrl(openURL?:"")
        }

        binding?.webView?.webViewClient = WebViewClient()


    }

    private fun initWebView() {
        binding?.webView?.overScrollMode = View.OVER_SCROLL_NEVER
        binding?.webView?.isNestedScrollingEnabled = false // 默认支持嵌套滑动

        // 设置自适应屏幕，两者合用
        binding?.webView?.settings?.useWideViewPort = true
        binding?.webView?.settings?.loadWithOverviewMode = true
        // 是否支持缩放，默认为true
        binding?.webView?.settings?.setSupportZoom(false)
        // 是否使用内置的缩放控件
        binding?.webView?.settings?.builtInZoomControls = false
        // 是否显示原生的缩放控件
        binding?.webView?.settings?.displayZoomControls = false
        // 设置文本缩放 默认 100
        binding?.webView?.settings?.textZoom = 100
        // 是否保存密码
        binding?.webView?.settings?.savePassword = false
        // 是否可以访问文件
        binding?.webView?.settings?.allowFileAccess = true
        //设置js功能打开
        binding?.webView?.settings?.javaScriptEnabled = true
        // 是否支持通过js打开新窗口
        binding?.webView?.settings?.javaScriptCanOpenWindowsAutomatically = true
        // 是否支持自动加载图片
        binding?.webView?.settings?.loadsImagesAutomatically = true
        binding?.webView?.settings?.blockNetworkImage = false
        // 设置编码格式
        binding?.webView?.settings?.defaultTextEncodingName = "utf-8"
        binding?.webView?.settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        // 是否启用 DOM storage API
        binding?.webView?.settings?.domStorageEnabled = true
        // 是否启用 database storage API 功能
        binding?.webView?.settings?.databaseEnabled = true
        // 配置当安全源试图从不安全源加载资源时WebView的行为
        binding?.webView?.settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        // 设置缓存模式
        binding?.webView?.settings?.cacheMode = WebSettings.LOAD_DEFAULT
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding?.webView != null) {
            val parent: ViewParent = binding?.webView?.parent ?:return
            (parent as ViewGroup).removeView(binding?.webView)
            binding?.webView?.removeAllViews()
            binding?.webView?.destroy()
        }
    }

}