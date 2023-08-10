package com.driving_school.home.practice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_Practice_PATH
import com.driving_school.R
import com.driving_school.databinding.ActivityPracticeBinding
import com.marshal.base_common.baseview.BaseViewActivity

/**
 * 顺序练习
 */
@Route(path = Driving_Practice_PATH)
class PracticeActivity : BaseViewActivity<ActivityPracticeBinding>() {

    /**
     * 0 练习
     * 1 考试
     */
    private var receiveType = 0

    // 1 科目1 4 科目4  3  c3
    private var subjectType = 0

    private var subjectOneFragment: TestAndExamSubjectOneFragment? = null
    private var subjectFourFragment: TestAndExamSubjectFourFragment? = null
    private var subjectThreeFragment: TestAndExamSubjectThreeFragment? = null

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun initView() {
        receiveType = intent.getIntExtra("testOrExam", 0)
        subjectType = intent.getIntExtra("subjectType", 0)

        if (hasIncludeToolbar) {
            val title = when (receiveType) {
                0 -> "顺序练习"
                1 -> "模拟考试"
                else -> ""
            }
            setTitle(title)
        }


        val bundle = Bundle()
        bundle.putInt("testOrExam", receiveType)
        bundle.putInt("subjectType", subjectType)

        val fragmentManager = supportFragmentManager.beginTransaction()
        when (subjectType) {
            1 -> {
                subjectOneFragment = TestAndExamSubjectOneFragment.getInstance(bundle)
                if (subjectOneFragment?.isAdded == false) {
                    fragmentManager.add(R.id.fragment_view, subjectOneFragment ?: return, "subject_one")
                        .commit()
                }
            }
            4 -> {
                subjectFourFragment = TestAndExamSubjectFourFragment.getInstance(bundle)
                if (subjectFourFragment?.isAdded == false) {
                    fragmentManager.add(R.id.fragment_view, subjectFourFragment ?: return, "subject_one")
                        .commit()
                }
            }

            3 -> {
                subjectThreeFragment = TestAndExamSubjectThreeFragment.getInstance(bundle)
                if (subjectThreeFragment?.isAdded == false) {
                    fragmentManager.add(R.id.fragment_view, subjectThreeFragment ?: return, "subject_one")
                        .commit()
                }
            }
        }

    }

}