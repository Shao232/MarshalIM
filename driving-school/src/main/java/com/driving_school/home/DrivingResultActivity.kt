package com.driving_school.home

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_Exam_Result_Path
import com.driving_school.databinding.ActivityExplainBinding
import com.marshal.base_common.baseview.BaseViewActivity

@Route(path = Driving_Exam_Result_Path)
class DrivingResultActivity : BaseViewActivity<ActivityExplainBinding>() {

    private var examScore: Int = 0

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityExplainBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        examScore = intent.getIntExtra("resultScore", 0)

        if(hasIncludeToolbar){
            setTitle("模拟结果")
        }


        binding?.tvExamExplainScore?.text = "${examScore}分"
        when(examScore) {
            90->{
                binding?.tvResultContent?.text = "恭喜你考试及格了"
            }
            else ->{
                binding?.tvResultContent?.text = "请继续努力"
            }
        }

    }

}