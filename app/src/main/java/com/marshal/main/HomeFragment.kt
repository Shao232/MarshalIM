package com.marshal.main

import NoShakeBtnUtil
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.marshal.AppRouterPath
import com.marshal.R
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.base_common.store.getAppAppLoginUserAccount
import com.marshal.databinding.FragmentHomeBinding


class HomeFragment : BaseViewFragment<FragmentHomeBinding>() {
    override fun getResLayoutId(): Int = R.layout.fragment_home

    override fun getResLayoutBinding(): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun hasToolbar(): Boolean = true

    override fun initView() {
         if(hasIncludeToolbar) {
             setTitle("首页")
         }

        binding?.tvChat?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener

            if(getAppAppLoginUserAccount().isNullOrEmpty()) {
                ARouter.getInstance().build(AppRouterPath.APP_LOGIN_PAGE).navigation()
                return@setOnClickListener
            }

            ARouter.getInstance().build(AppRouterPath.CHAT_PATH).navigation()
        }

        binding?.tvToFunction?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(AppRouterPath.APP_FUNCTION_PAGE).navigation(mContext)
        }

    }
}