package com.marshal

import NoShakeBtnUtil
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.marshal.base_common.baseview.BaseViewFragment
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
            ARouter.getInstance().build(IMPath.CHAT_PATH).navigation()
        }

        binding?.tvQuestion?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(IMPath.OPEN_QUESTION_BANK).navigation(mContext)
        }

        binding?.tvCoroutines?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(IMPath.HOME_COROUTINES).navigation(mContext)
        }

    }


}