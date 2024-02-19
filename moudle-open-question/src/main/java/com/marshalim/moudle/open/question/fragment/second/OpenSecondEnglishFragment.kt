package com.marshalim.moudle.open.question.fragment.second

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshalim.moudle.open.question.adapter.OpenSecondEnglishAdapter
import com.marshalim.moudle.open.question.fragment.OpenQuestionBaseFragment
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import getAppSecondEnglishQuestionData

class OpenSecondEnglishFragment : OpenQuestionBaseFragment() {

    private var adapter = OpenSecondEnglishAdapter()
    private var appOpenSecondEnglishQuestions: ArrayList<OpenAnswersBean> = ArrayList()

    override fun getAdapter() {
        binding?.recyclerList?.adapter = adapter
    }

    override fun viewCreate() {
        super.viewCreate()

        if (hasIncludeToolbar) {
            setTitle("大学英语2")
        }

        val appSecondEnglishData = getAppSecondEnglishQuestionData()
        if (appSecondEnglishData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appOpenSecondEnglishQuestions = Gson().fromJson(appSecondEnglishData, type)
        }

        adapter.addListAll(appOpenSecondEnglishQuestions)
    }

}