package com.marshalim.moudle.open.question

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshalim.moudle.open.question.adapter.OpenQuestionProgramDesignAdapter
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import getAppProgramSingleData


/**
 * 程序设计
 */
class OpenProgramDesignFragment : OpenQuestionBaseFragment()  {

    private var adapter= OpenQuestionProgramDesignAdapter()
    private var programSingleQuestions =  ArrayList<OpenAnswersBean>()


    override fun getAdapter() {
        binding?.recyclerList?.adapter = adapter
    }

    override fun viewCreate() {
        super.viewCreate()
        val appProgramSingleData = getAppProgramSingleData()
        if(appProgramSingleData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            programSingleQuestions = Gson().fromJson(appProgramSingleData,type)
        }
        adapter.addListAll(programSingleQuestions)
    }

}