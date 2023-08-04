package com.driving_school.home

import android.view.View
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.driving_school.R
import com.driving_school.databinding.FragSubjectOneBinding
import com.marshal.base_common.baseview.BaseViewFragment

class DrivingHomeFragment : BaseViewFragment<FragSubjectOneBinding>() {

    private val viewModel: DrivingSchoolViewModel by activityViewModels()

    override fun getResLayoutId(): Int = R.layout.frag_subject_one

    override fun getResLayoutBinding(): View? {
        binding = FragSubjectOneBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        viewModel.iniSubjectOneData()
        viewModel.initSubjectFourData()

        binding?.lvnExam?.setOnClickListener {
            //模拟考试
            showSelectSubjectDialog(1)
        }

        binding?.lvnPractice?.setOnClickListener {
            //顺序练习
            showSelectSubjectDialog(0)
        }

        binding?.lvnCollection?.setOnClickListener {
            //收藏
            ARouter.getInstance().build(DrivingRouterPath.Driving_Collection_Error_Path)
                .withInt("collectionOrError", 2)
                .navigation()
        }

        binding?.lvnError?.setOnClickListener {
            //错题
            ARouter.getInstance().build(DrivingRouterPath.Driving_Collection_Error_Path)
                .withInt("collectionOrError", 3)
                .navigation()
        }

    }

    private fun showSelectSubjectDialog(textOrExam:Int) {
        val dialog = SelectSubjectDialog()
            .setSubjectOneContent(viewModel.drivingSubjectOneSize)
            .setSubjectFourContent(viewModel.drivingSubjectFourSize)
        dialog.setOnSelectSubjectItemClick(object : SelectSubjectDialog.SelectSubjectClick {
            override fun onSelectSubjectClick(subjectType: Int) {
                when (subjectType) {
                    1 -> {
                        ARouter.getInstance().build(DrivingRouterPath.Driving_Practice_PATH)
                            .withInt("testOrExam", textOrExam)
                            .withInt("subjectType",subjectType)
                            .navigation()
                    }

                    4 -> {
                        ARouter.getInstance().build(DrivingRouterPath.Driving_Practice_PATH)
                            .withInt("testOrExam", textOrExam)
                            .withInt("subjectType",subjectType)
                            .navigation()
                    }
                }
            }
        })
        dialog.show(childFragmentManager, "select_subject_dialog")
    }


}