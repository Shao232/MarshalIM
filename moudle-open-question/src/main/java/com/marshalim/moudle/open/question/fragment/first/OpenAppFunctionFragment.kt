package com.marshalim.moudle.open.question.fragment.first

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshalim.moudle.open.question.adapter.OpenQuestionAdapter
import com.marshalim.moudle.open.question.fragment.OpenQuestionBaseFragment
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import getAppFunctionEstimateData
import getAppFunctionMultipleData
import getAppFunctionSingleData


/**
 * 应用基础
 */
class OpenAppFunctionFragment : OpenQuestionBaseFragment() {

    private val adapter = OpenQuestionAdapter()

    private var appSingleQuestions = ArrayList<OpenAnswersBean>()
    private var appMultipleQuestions = ArrayList<OpenAnswersBean>()
    private var appEstimateQuestions =  ArrayList<OpenAnswersBean>()

    override fun getAdapter() {
        binding?.recyclerList?.adapter = adapter
    }

    override fun viewCreate() {
        super.viewCreate()

        if(hasIncludeToolbar) {
            setTitle("计算机应用基础")
        }

        val appFunctionSingleData = getAppFunctionSingleData()
        if(appFunctionSingleData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appSingleQuestions =Gson().fromJson(appFunctionSingleData,type)
        }

        val appFunctionMultipleData = getAppFunctionMultipleData()
        if(appFunctionMultipleData?.isNotEmpty() == true) {
            val type2 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appMultipleQuestions = Gson().fromJson(appFunctionMultipleData,type2)
        }

        val appFunctionEstimateData = getAppFunctionEstimateData()
        if(appFunctionEstimateData?.isNotEmpty() == true) {
            val type3 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appEstimateQuestions = Gson().fromJson(appFunctionEstimateData,type3)
        }

        adapter.addListAll(appSingleQuestions)
    }

    fun updateList(type:Int){
        adapter.itemList.clear()
        when(type){
            1->{
                adapter.addListAll(appSingleQuestions)
            }
            2->{
                adapter.addListAll(appMultipleQuestions)
            }
            3->{
                adapter.addListAll(appEstimateQuestions)
            }
        }
        layoutManager?.scrollToPosition(0)
    }
}