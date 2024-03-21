package com.marshalim.moudle.open.question.fragment.third

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshalim.moudle.open.question.adapter.OpenReadQuestionAdapter
import com.marshalim.moudle.open.question.fragment.OpenQuestionBaseFragment
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import getAppThirdDatabaseData

class OpenDatabaseFragment : OpenQuestionBaseFragment() {

    private var appThirdDatabaseList: ArrayList<OpenAnswersBean>? = null
    private var readQuestionAdapter:OpenReadQuestionAdapter? = OpenReadQuestionAdapter()

    override fun getAdapter() {
        binding?.recyclerList?.adapter = readQuestionAdapter
    }

    override fun viewCreate() {
        super.viewCreate()

        if (hasIncludeToolbar) {
            setTitle("数据库及原理")
        }


        val appThirdDatabaseData = getAppThirdDatabaseData()
        if (appThirdDatabaseData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appThirdDatabaseList = Gson().fromJson(appThirdDatabaseData, type)
        }

        if(appThirdDatabaseList?.isNotEmpty() == true) {
//            for (itemBean: OpenAnswersBean in appThirdDatabaseList?:return) {
//                Log.d("OpenDatabaseFragment", "bean :$itemBean")
//            }

            readQuestionAdapter?.addListAll(appThirdDatabaseList?:return)
        }
    }

}