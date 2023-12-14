package com.marshalim.moudle.open.question

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView

object WebViewUtils {

    @SuppressLint("SetJavaScriptEnabled")
    fun defaultSettings(webView: WebView) {
        // 白色背景

        webView.overScrollMode = View.OVER_SCROLL_NEVER
        webView.isNestedScrollingEnabled = false // 默认支持嵌套滑动

        // 设置自适应屏幕，两者合用
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        // 是否支持缩放，默认为true
        webView.settings.setSupportZoom(false)
        // 是否使用内置的缩放控件
        webView.settings.builtInZoomControls = false
        // 是否显示原生的缩放控件
        webView.settings.displayZoomControls = false
        // 设置文本缩放 默认 100
        webView.settings.textZoom = 100
        // 是否保存密码
        webView.settings.savePassword = false
        // 是否可以访问文件
        webView.settings.allowFileAccess = true
        //设置js功能打开
        webView.settings.javaScriptEnabled = true
        // 是否支持通过js打开新窗口
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        // 是否支持自动加载图片
        webView.settings.loadsImagesAutomatically = true
        webView.settings.blockNetworkImage = false
        // 设置编码格式
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        // 是否启用 DOM storage API
        webView.settings.domStorageEnabled = true
        // 是否启用 database storage API 功能
        webView.settings.databaseEnabled = true
        // 配置当安全源试图从不安全源加载资源时WebView的行为
        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        // 设置缓存模式
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
    }

}