package com.marshal.mine.open

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.mine.open.adapter.OpenSecondEnglishAdapter
import com.marshal.pojo.OpenAnswersBean
import getAppSecondEnglishQuestionData

class OpenSecondEnglishFragment:OpenQuestionBaseFragment()   {

    private var adapter= OpenSecondEnglishAdapter()
    private var appOpenSecondEnglishQuestions:ArrayList<OpenAnswersBean> = ArrayList()

    override fun getAdapter() {
        binding?.recyclerList?.adapter = adapter
    }

    override fun viewCreate() {
        super.viewCreate()
        val appSecondEnglishData = getAppSecondEnglishQuestionData()
        if(appSecondEnglishData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appOpenSecondEnglishQuestions = Gson().fromJson(appSecondEnglishData,type)
        }

        adapter.addListAll(appOpenSecondEnglishQuestions)
    }

}