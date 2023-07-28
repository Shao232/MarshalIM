package com.driving_school.home

import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_Practice_PATH
import com.driving_school.bean.DrivingBean
import com.driving_school.databinding.ActivityPracticeBinding
import com.driving_school.home.adapter.PracticeAdapter
import com.google.gson.Gson
import com.marshal.base_common.baseview.BaseViewActivity
import getDrivingSubjectFour
import getDrivingSubjectOne

/**
 * 顺序练习
 */
@Route(path = Driving_Practice_PATH)
class PracticeActivity : BaseViewActivity<ActivityPracticeBinding>() {

    // 0 练习 1 考试
    private var receiveType = 0
    private var adapter: PracticeAdapter? = null
    private var subjectType = 0

    //answerCorrect 正确回答的数量 answerError 错误回答的数量
    private var answerCorrect: Int = 0
    private var answerError: Int = 0

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        receiveType = intent.getIntExtra("testOrExam", 0)
        subjectType = intent.getIntExtra("subjectType", 0)

        if (hasIncludeToolbar) {
            val title = if (receiveType == 0) "顺序练习" else "模拟考试"
            setTitle(title)
        }

        binding?.lvnCollectionAndError?.visibility = View.VISIBLE
        if (receiveType == 0) {
            //练习 倒计时隐藏
            binding?.lvnTimeSubject?.visibility = View.GONE
            binding?.lvnCollectionSubject?.visibility = View.VISIBLE
            binding?.lvnTestCorrectSubject?.visibility = View.VISIBLE
            binding?.lvnTestErrorSubject?.visibility = View.VISIBLE
        } else {
            binding?.lvnTimeSubject?.visibility = View.VISIBLE
        }

        adapter = PracticeAdapter()
        setSubjectData()

        binding?.viewpagerSubject?.adapter = adapter
        binding?.viewpagerSubject?.offscreenPageLimit = 5
        //禁止用户滑动
        binding?.viewpagerSubject?.isUserInputEnabled = false
        adapter?.setOnNextQuestionSelect(object : PracticeAdapter.PracticeItemSelectClick {
            override fun onItemOneSelectClick(position: Int, direction: Int) {
                val count = if (adapter?.itemCount == 0) 0 else (adapter?.itemCount ?: 0) - 1
                if (direction == 1) {
                    val beforePosition = position - 1
                    binding?.viewpagerSubject?.currentItem =
                        if (beforePosition <= 0) 0 else beforePosition
                } else {
                    if (position >= count) {
                        //如果是最后一题，完成全部练习
                    } else {
                        val nextPosition = position + 1
                        //点击切换到下一题
                        binding?.viewpagerSubject?.currentItem =
                            if (nextPosition >= count) position else nextPosition
                    }
                }
            }

            override fun onItemTwoSelectQuestion(position: Int, correct: Boolean) {
                val bean = adapter?.itemList?.get(position)
                //当每一题判断是否回答正确
                bean?.isAnswerCorrect = correct
                bean?.answerFrequency = (bean?.answerFrequency?:0) + 1



            }

        })
    }

    private fun setSubjectData() {
        if (subjectType == 1) {
            val subjectOneJson = getDrivingSubjectOne()
            val gson = Gson()
            val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
            val questionList = data.result
            adapter?.itemList?.clear()
            questionList?.let { adapter?.itemList?.addAll(it) }
        } else {
            val subjectOneJson = getDrivingSubjectFour()
            val gson = Gson()
            val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
            val questionList = data.result
            adapter?.itemList?.clear()
            questionList?.let { adapter?.itemList?.addAll(it) }
        }
    }
}