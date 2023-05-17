package com.marshal

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.marshal.baseview.BaseViewFragment
import com.marshal.databinding.FragmentHomeBinding

class HomeFragment : BaseViewFragment<FragmentHomeBinding>() {
    override fun getResLayoutId(): Int = R.layout.fragment_home

    override fun getResLayoutBinding(): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        binding?.tvChat?.setOnClickListener {
            ARouter.getInstance().build(IMPath.CHAT_PATH).navigation()

        }

        binding?.tvPhone?.setOnClickListener {
            ARouter.getInstance().build(IMPath.PHONE_PAGE).navigation()
        }


    }


}