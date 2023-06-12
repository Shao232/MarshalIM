package com.marshal.mine.open

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.mine.adapter.OpenQuestionAdapter
import com.marshal.pojo.OpenAnswersBean
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

    override fun getAdapter():  RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return adapter as?  RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun viewCreate() {
        super.viewCreate()
        val appFunctionSingleData = getAppFunctionSingleData()
        if(appFunctionSingleData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appSingleQuestions =Gson().fromJson(appFunctionSingleData,type)
            Log.d("TAG","save appfunction first :${appSingleQuestions.size}")
        }

        val appFunctionMultipleData = getAppFunctionMultipleData()
        if(appFunctionMultipleData?.isNotEmpty() == true) {
            val type2 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appMultipleQuestions = Gson().fromJson(appFunctionMultipleData,type2)
            Log.d("TAG","save appfunction second :${appMultipleQuestions.size}")
        }

        val appFunctionEstimateData = getAppFunctionEstimateData()
        if(appFunctionEstimateData?.isNotEmpty() == true) {
            val type3 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appEstimateQuestions = Gson().fromJson(appFunctionEstimateData,type3)
            Log.d("TAG","save appfunction third :${appEstimateQuestions.size}")
        }

        adapter.addListAll(appSingleQuestions)
    }

    override fun onResume() {
        super.onResume()
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