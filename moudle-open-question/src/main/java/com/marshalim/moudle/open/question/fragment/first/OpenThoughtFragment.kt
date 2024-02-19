package com.marshalim.moudle.open.question.fragment.first

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshalim.moudle.open.question.adapter.OpenQuestionThoughtAdapter
import com.marshalim.moudle.open.question.fragment.OpenQuestionBaseFragment
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import getAppThoughtSingleData


/**
 * 思维导论
 */

class OpenThoughtFragment : OpenQuestionBaseFragment()  {

    private var adapter = OpenQuestionThoughtAdapter()
    private var thoughtSingleQuestions =  ArrayList<OpenAnswersBean>()

    override fun getAdapter() {
        binding?.recyclerList?.adapter = adapter
    }

    override fun viewCreate() {
        super.viewCreate()

        if(hasIncludeToolbar) {
            setTitle("计算机思维导论")
        }

        val appThoughtSingleData = getAppThoughtSingleData()
        if(appThoughtSingleData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            thoughtSingleQuestions = Gson().fromJson(appThoughtSingleData,type)
        }

        adapter.addListAll(thoughtSingleQuestions)
    }

}