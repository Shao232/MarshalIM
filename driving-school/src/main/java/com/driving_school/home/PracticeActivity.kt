package com.driving_school.home

import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_Practice_PATH
import com.driving_school.bean.DrivingBean
import com.driving_school.databinding.ActivityPracticeBinding
import com.driving_school.home.adapter.PracticeAdapter
import com.google.gson.Gson
import com.marshal.base_common.baseview.BaseViewActivity
import getDrivingSubjectOne

/**
 * 顺序练习
 */
@Route(path = Driving_Practice_PATH)
class PracticeActivity : BaseViewActivity<ActivityPracticeBinding>() {

    // 0 练习 1 考试
    private var receiveType = 0
    private var adapter:PracticeAdapter? = null

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        receiveType = intent.getIntExtra("testOrExam",0)
        if(hasIncludeToolbar) {
            val title = if(receiveType == 0) "顺序练习" else "模拟考试"
            setTitle(title)
        }

        adapter = PracticeAdapter()
        initData()
        binding?.viewpagerSubject?.adapter = adapter

    }

    private fun initData() {
        val subjectOneJson = getDrivingSubjectOne()
        val gson = Gson()

        val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
        Log.d("TAG","data:${data}")
        val questionList = data.result
        questionList?.let { adapter?.addListAll(it) }
    }
}