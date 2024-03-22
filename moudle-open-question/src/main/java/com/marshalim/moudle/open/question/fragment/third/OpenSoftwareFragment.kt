package com.marshalim.moudle.open.question.fragment.third

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshalim.moudle.open.question.adapter.OpenReadQuestionAdapter
import com.marshalim.moudle.open.question.fragment.OpenQuestionBaseFragment
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import getAppThirdSoftwareData

class OpenSoftwareFragment : OpenQuestionBaseFragment()  {

    private var appThirdSoftwareList: ArrayList<OpenAnswersBean>? = null
    private var readQuestionAdapter: OpenReadQuestionAdapter? = OpenReadQuestionAdapter()

    override fun getAdapter() {
        binding?.recyclerList?.adapter = readQuestionAdapter
    }


    override fun viewCreate() {
        super.viewCreate()

        if (hasIncludeToolbar) {
            setTitle("软件工程复习题")
        }

        val appThirdSoftwareData = getAppThirdSoftwareData()
        if (appThirdSoftwareData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appThirdSoftwareList = Gson().fromJson(appThirdSoftwareData, type)
        }

        if(appThirdSoftwareList?.isNotEmpty() == true) {
            readQuestionAdapter?.addListAll(appThirdSoftwareList?:return)
        }

    }


}