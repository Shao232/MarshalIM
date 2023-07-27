package com.driving_school.home

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.driving_school.R
import com.driving_school.databinding.FragSubjectOneBinding
import com.marshal.base_common.baseview.BaseViewFragment

class DrivingHomeFragment : BaseViewFragment<FragSubjectOneBinding>() {

    override fun getResLayoutId(): Int = R.layout.frag_subject_one

    override fun getResLayoutBinding(): View? {
        binding = FragSubjectOneBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        binding?.lvnExam?.setOnClickListener {
            //模拟考试
            ARouter.getInstance().build(DrivingRouterPath.Driving_Practice_PATH)
                .withInt("testOrExam", 1)
                .navigation()
        }

        binding?.lvnPractice?.setOnClickListener {
            //顺序练习
            ARouter.getInstance().build(DrivingRouterPath.Driving_Practice_PATH)
                .withInt("testOrExam", 0)
                .navigation()
        }

        binding?.lvnCollection?.setOnClickListener {
            //收藏

        }

        binding?.lvnError?.setOnClickListener {
            //错题

        }

    }


}