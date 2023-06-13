package com.marshal.mine.open

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.mine.open.adapter.OpenQuestionThoughtAdapter
import com.marshal.pojo.OpenAnswersBean
import getAppThoughtSingleData


/**
 * 思维导论
 */

class OpenThoughtFragment :OpenQuestionBaseFragment()  {

    private var adapter = OpenQuestionThoughtAdapter()
    private var thoughtSingleQuestions =  ArrayList<OpenAnswersBean>()
    override fun getAdapter():  RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return adapter as?  RecyclerView.Adapter<RecyclerView.ViewHolder>?
    }

    override fun viewCreate() {
        super.viewCreate()
        val appThoughtSingleData = getAppThoughtSingleData()
        if(appThoughtSingleData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            thoughtSingleQuestions = Gson().fromJson(appThoughtSingleData,type)
        }

        adapter.addListAll(thoughtSingleQuestions)
    }

}